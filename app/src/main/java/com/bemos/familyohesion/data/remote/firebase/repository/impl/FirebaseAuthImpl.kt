package com.bemos.familyohesion.data.remote.firebase.repository.impl

import android.util.Log
import com.bemos.familyohesion.domain.models.Category
import com.bemos.familyohesion.domain.models.Family
import com.bemos.familyohesion.domain.models.Skill
import com.bemos.familyohesion.domain.models.SubSkill
import com.bemos.familyohesion.domain.models.UserAuth
import com.bemos.familyohesion.domain.repositories.FirebaseAuthRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class FirebaseAuthImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
): FirebaseAuthRepository {
    override fun getCurrentUserId(): String? {
        val currentUser = firebaseAuth.currentUser
        return currentUser?.uid
    }

    override fun signUp(userAuth: UserAuth, familyName: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(userAuth.email, userAuth.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = firebaseAuth.currentUser?.uid ?: return@addOnCompleteListener
                    val familyId = UUID.randomUUID().toString()

                    val newFamily = Family(
                        familyId = familyId,
                        name = familyName,
                        adminId = userId,
                        members = listOf(
                            mapOf(
                                "name" to userAuth.name,
                                "points" to 0,
                                "relation" to userAuth.userRole,
                                "userId" to userId
                            )
                        )
                    )

                    val familyDocRef = firebaseFirestore.collection("families").document(familyId)
                    val userDocRef = firebaseFirestore.collection("users").document(userId)

                    firebaseFirestore.runBatch { batch ->
                        batch.set(familyDocRef, newFamily)
                        batch.set(
                            userDocRef,
                            mapOf(
                                "userId" to userId,
                                "email" to userAuth.email,
                                "name" to userAuth.name,
                                "familyId" to familyId,
                                "role" to userAuth.userRole
                            )
                        )
                    }.addOnSuccessListener {
                        onSuccess()
                    }.addOnFailureListener { exception ->
                        onFailure(exception)
                    }
                } else {
                    task.exception?.let { onFailure(it) }
                }
            }
    }

    override fun signUpAndJoinFamily(
        userAuth: UserAuth,
        familyId: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val currentUser = firebaseAuth.currentUser

        firebaseAuth.createUserWithEmailAndPassword(userAuth.email, userAuth.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = task.result?.user?.uid ?: return@addOnCompleteListener

                    val userDocRef = firebaseFirestore.collection("users").document(userId)
                    val familyDocRef = firebaseFirestore.collection("families").document(familyId)

                    firebaseFirestore.runBatch { batch ->
                        val newMember = mapOf(
                            "userId" to userId,
                            "name" to userAuth.name,
                            "points" to 0,
                            "relation" to userAuth.userRole
                        )
                        batch.update(familyDocRef, "members", FieldValue.arrayUnion(newMember))
                        batch.set(
                            userDocRef,
                            mapOf(
                                "userId" to userId,
                                "email" to userAuth.email,
                                "name" to userAuth.name,
                                "familyId" to familyId,
                                "role" to userAuth.userRole
                            )
                        )
                    }.addOnSuccessListener {
                        currentUser?.let { firebaseAuth.updateCurrentUser(it) }
                        onSuccess()
                    }.addOnFailureListener { exception ->
                        onFailure(exception)
                    }
                } else {
                    task.exception?.let { onFailure(it) }
                }
            }
    }

    override fun signIn(userAuth: UserAuth, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(userAuth.email, userAuth.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    task.exception?.let { onFailure(it) }
                }
            }

//        val categories = mapOf(
//            "category1" to Category(id = "category1", name = "Кулинария"),
//            "category2" to Category(id = "category2", name = "Рисование"),
//            "category3" to Category(id = "category3", name = "Уборка"),
//            "category4" to Category(id = "category4", name = "Сад и огород"),
//            "category5" to Category(id = "category5", name = "Ремонт"),
//            "category6" to Category(id = "category6", name = "Семейные игры")
//        )
//
//        val skills = mapOf(
//            // Кулинария (6 навыков)
//            "skill1" to Skill(
//                id = "skill1",
//                name = "Приготовление пиццы",
//                categoryId = "category1",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill1", name = "Приготовить пеперони", points = 3, skillId = "skill1"),
//                    SubSkill(id = "subSkill2", name = "Приготовить 4 сыра", points = 2, skillId = "skill1"),
//                    SubSkill(id = "subSkill3", name = "Приготовить маргариту", points = 2, skillId = "skill1"),
//                    SubSkill(id = "subSkill4", name = "Приготовить охотничью", points = 1, skillId = "skill1"),
//                    SubSkill(id = "subSkill5", name = "Приготовить грибную пиццу", points = 2, skillId = "skill1"),
//                    SubSkill(id = "subSkill6", name = "Приготовить овощную пиццу", points = 1, skillId = "skill1"),
//                    SubSkill(id = "subSkill7", name = "Приготовить пиццу с морепродуктами", points = 3, skillId = "skill1")
//                )
//            ),
//            "skill2" to Skill(
//                id = "skill2",
//                name = "Приготовление супов",
//                categoryId = "category1",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill8", name = "Сварить борщ", points = 3, skillId = "skill2"),
//                    SubSkill(id = "subSkill9", name = "Сварить рассольник", points = 2, skillId = "skill2"),
//                    SubSkill(id = "subSkill10", name = "Сварить куриный суп", points = 1, skillId = "skill2"),
//                    SubSkill(id = "subSkill11", name = "Сварить крем-суп из грибов", points = 2, skillId = "skill2"),
//                    SubSkill(id = "subSkill12", name = "Сварить овощной суп", points = 1, skillId = "skill2"),
//                    SubSkill(id = "subSkill13", name = "Сварить рыбный суп", points = 3, skillId = "skill2")
//                )
//            ),
//            "skill10" to Skill(
//                id = "skill10",
//                name = "Выпечка",
//                categoryId = "category1",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill43", name = "Испечь пирог с яблоками", points = 3, skillId = "skill10"),
//                    SubSkill(id = "subSkill44", name = "Испечь кексы", points = 2, skillId = "skill10"),
//                    SubSkill(id = "subSkill45", name = "Приготовить печенье", points = 1, skillId = "skill10"),
//                    SubSkill(id = "subSkill46", name = "Испечь хлеб", points = 3, skillId = "skill10"),
//                    SubSkill(id = "subSkill47", name = "Приготовить круассаны", points = 2, skillId = "skill10")
//                )
//            ),
//            "skill11" to Skill(
//                id = "skill11",
//                name = "Приготовление салатов",
//                categoryId = "category1",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill48", name = "Приготовить цезарь", points = 2, skillId = "skill11"),
//                    SubSkill(id = "subSkill49", name = "Приготовить греческий салат", points = 2, skillId = "skill11"),
//                    SubSkill(id = "subSkill50", name = "Приготовить оливье", points = 3, skillId = "skill11"),
//                    SubSkill(id = "subSkill51", name = "Приготовить овощной салат", points = 1, skillId = "skill11")
//                )
//            ),
//            "skill12" to Skill(
//                id = "skill12",
//                name = "Гриль и барбекю",
//                categoryId = "category1",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill52", name = "Приготовить стейк", points = 3, skillId = "skill12"),
//                    SubSkill(id = "subSkill53", name = "Приготовить шашлык", points = 3, skillId = "skill12"),
//                    SubSkill(id = "subSkill54", name = "Запечь овощи на гриле", points = 2, skillId = "skill12"),
//                    SubSkill(id = "subSkill55", name = "Приготовить куриные крылья", points = 2, skillId = "skill12")
//                )
//            ),
//            "skill13" to Skill(
//                id = "skill13",
//                name = "Десерты",
//                categoryId = "category1",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill56", name = "Приготовить тирамису", points = 3, skillId = "skill13"),
//                    SubSkill(id = "subSkill57", name = "Приготовить пудинг", points = 2, skillId = "skill13"),
//                    SubSkill(id = "subSkill58", name = "Приготовить мороженое", points = 3, skillId = "skill13"),
//                    SubSkill(id = "subSkill59", name = "Приготовить фруктовый салат", points = 1, skillId = "skill13")
//                )
//            ),
//
//            // Рисование (6 навыков)
//            "skill3" to Skill(
//                id = "skill3",
//                name = "Основы рисования",
//                categoryId = "category2",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill14", name = "Нарисовать домик", points = 1, skillId = "skill3"),
//                    SubSkill(id = "subSkill15", name = "Нарисовать дерево", points = 1, skillId = "skill3"),
//                    SubSkill(id = "subSkill16", name = "Нарисовать солнце", points = 1, skillId = "skill3"),
//                    SubSkill(id = "subSkill17", name = "Нарисовать радугу", points = 1, skillId = "skill3")
//                )
//            ),
//            "skill4" to Skill(
//                id = "skill4",
//                name = "Цвета и тени",
//                categoryId = "category2",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill18", name = "Закрасить круг одним цветом", points = 1, skillId = "skill4"),
//                    SubSkill(id = "subSkill19", name = "Сделать градиент от темного к светлому", points = 2, skillId = "skill4"),
//                    SubSkill(id = "subSkill20", name = "Нарисовать тень от объекта", points = 2, skillId = "skill4"),
//                    SubSkill(id = "subSkill21", name = "Смешать два цвета", points = 2, skillId = "skill4")
//                )
//            ),
//            "skill14" to Skill(
//                id = "skill14",
//                name = "Рисование животных",
//                categoryId = "category2",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill60", name = "Нарисовать кошку", points = 2, skillId = "skill14"),
//                    SubSkill(id = "subSkill61", name = "Нарисовать собаку", points = 2, skillId = "skill14"),
//                    SubSkill(id = "subSkill62", name = "Нарисовать птицу", points = 1, skillId = "skill14"),
//                    SubSkill(id = "subSkill63", name = "Нарисовать рыбу", points = 1, skillId = "skill14")
//                )
//            ),
//            "skill15" to Skill(
//                id = "skill15",
//                name = "Портреты",
//                categoryId = "category2",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill64", name = "Нарисовать лицо человека", points = 3, skillId = "skill15"),
//                    SubSkill(id = "subSkill65", name = "Нарисовать глаза", points = 2, skillId = "skill15"),
//                    SubSkill(id = "subSkill66", name = "Нарисовать волосы", points = 2, skillId = "skill15"),
//                    SubSkill(id = "subSkill67", name = "Нарисовать улыбку", points = 1, skillId = "skill15")
//                )
//            ),
//            "skill16" to Skill(
//                id = "skill16",
//                name = "Пейзажи",
//                categoryId = "category2",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill68", name = "Нарисовать горы", points = 3, skillId = "skill16"),
//                    SubSkill(id = "subSkill69", name = "Нарисовать реку", points = 2, skillId = "skill16"),
//                    SubSkill(id = "subSkill70", name = "Нарисовать лес", points = 3, skillId = "skill16"),
//                    SubSkill(id = "subSkill71", name = "Нарисовать закат", points = 2, skillId = "skill16")
//                )
//            ),
//            "skill17" to Skill(
//                id = "skill17",
//                name = "Абстрактное искусство",
//                categoryId = "category2",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill72", name = "Создать абстрактную композицию", points = 3, skillId = "skill17"),
//                    SubSkill(id = "subSkill73", name = "Использовать геометрические фигуры", points = 2, skillId = "skill17"),
//                    SubSkill(id = "subSkill74", name = "Создать цветовой контраст", points = 2, skillId = "skill17"),
//                    SubSkill(id = "subSkill75", name = "Нарисовать спираль", points = 1, skillId = "skill17")
//                )
//            ),
//
//            // Уборка (6 навыков)
//            "skill5" to Skill(
//                id = "skill5",
//                name = "Генеральная уборка",
//                categoryId = "category3",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill22", name = "Убрать гостиную", points = 3, skillId = "skill5"),
//                    SubSkill(id = "subSkill23", name = "Убрать кухню", points = 3, skillId = "skill5"),
//                    SubSkill(id = "subSkill24", name = "Убрать ванную комнату", points = 2, skillId = "skill5"),
//                    SubSkill(id = "subSkill25", name = "Пропылесосить весь дом", points = 2, skillId = "skill5"),
//                    SubSkill(id = "subSkill26", name = "Протереть пыль", points = 1, skillId = "skill5")
//                )
//            ),
//            "skill18" to Skill(
//                id = "skill18",
//                name = "Уборка спальни",
//                categoryId = "category3",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill76", name = "Заправить кровать", points = 1, skillId = "skill18"),
//                    SubSkill(id = "subSkill77", name = "Убрать одежду в шкаф", points = 2, skillId = "skill18"),
//                    SubSkill(id = "subSkill78", name = "Протереть зеркала", points = 1, skillId = "skill18"),
//                    SubSkill(id = "subSkill79", name = "Пропылесосить ковер", points = 2, skillId = "skill18")
//                )
//            ),
//            "skill19" to Skill(
//                id = "skill19",
//                name = "Мытье посуды",
//                categoryId = "category3",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill80", name = "Помыть тарелки", points = 1, skillId = "skill19"),
//                    SubSkill(id = "subSkill81", name = "Помыть кастрюли", points = 2, skillId = "skill19"),
//                    SubSkill(id = "subSkill82", name = "Помыть стаканы", points = 1, skillId = "skill19"),
//                    SubSkill(id = "subSkill83", name = "Протереть посуду", points = 1, skillId = "skill19")
//                )
//            ),
//            "skill20" to Skill(
//                id = "skill20",
//                name = "Стирка",
//                categoryId = "category3",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill84", name = "Загрузить стиральную машину", points = 2, skillId = "skill20"),
//                    SubSkill(id = "subSkill85", name = "Развесить белье", points = 2, skillId = "skill20"),
//                    SubSkill(id = "subSkill86", name = "Погладить одежду", points = 3, skillId = "skill20"),
//                    SubSkill(id = "subSkill87", name = "Сложить белье", points = 1, skillId = "skill20")
//                )
//            ),
//            "skill21" to Skill(
//                id = "skill21",
//                name = "Организация пространства",
//                categoryId = "category3",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill88", name = "Разобрать шкаф", points = 3, skillId = "skill21"),
//                    SubSkill(id = "subSkill89", name = "Организовать полки", points = 2, skillId = "skill21"),
//                    SubSkill(id = "subSkill90", name = "Убрать игрушки", points = 1, skillId = "skill21"),
//                    SubSkill(id = "subSkill91", name = "Рассортировать вещи", points = 2, skillId = "skill21")
//                )
//            ),
//            "skill22" to Skill(
//                id = "skill22",
//                name = "Чистка поверхностей",
//                categoryId = "category3",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill92", name = "Помыть окна", points = 3, skillId = "skill22"),
//                    SubSkill(id = "subSkill93", name = "Почистить плиту", points = 2, skillId = "skill22"),
//                    SubSkill(id = "subSkill94", name = "Протереть столы", points = 1, skillId = "skill22"),
//                    SubSkill(id = "subSkill95", name = "Почистить раковину", points = 2, skillId = "skill22")
//                )
//            ),
//
//            // Сад и огород (6 навыков)
//            "skill6" to Skill(
//                id = "skill6",
//                name = "Работа в саду",
//                categoryId = "category4",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill27", name = "Посадить цветы", points = 2, skillId = "skill6"),
//                    SubSkill(id = "subSkill28", name = "Полить растения", points = 1, skillId = "skill6"),
//                    SubSkill(id = "subSkill29", name = "Прополоть грядки", points = 2, skillId = "skill6"),
//                    SubSkill(id = "subSkill30", name = "Собрать урожай", points = 3, skillId = "skill6")
//                )
//            ),
//            "skill23" to Skill(
//                id = "skill23",
//                name = "Уход за деревьями",
//                categoryId = "category4",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill96", name = "Обрезать ветки", points = 3, skillId = "skill23"),
//                    SubSkill(id = "subSkill97", name = "Посадить дерево", points = 3, skillId = "skill23"),
//                    SubSkill(id = "subSkill98", name = "Удобрить почву", points = 2, skillId = "skill23"),
//                    SubSkill(id = "subSkill99", name = "Собрать фрукты", points = 2, skillId = "skill23")
//                )
//            ),
//            "skill24" to Skill(
//                id = "skill24",
//                name = "Огородничество",
//                categoryId = "category4",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill100", name = "Посадить помидоры", points = 2, skillId = "skill24"),
//                    SubSkill(id = "subSkill101", name = "Посадить огурцы", points = 2, skillId = "skill24"),
//                    SubSkill(id = "subSkill102", name = "Посадить зелень", points = 1, skillId = "skill24"),
//                    SubSkill(id = "subSkill103", name = "Собрать овощи", points = 3, skillId = "skill24")
//                )
//            ),
//            "skill25" to Skill(
//                id = "skill25",
//                name = "Уход за газоном",
//                categoryId = "category4",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill104", name = "Подстричь газон", points = 2, skillId = "skill25"),
//                    SubSkill(id = "subSkill105", name = "Полить газон", points = 1, skillId = "skill25"),
//                    SubSkill(id = "subSkill106", name = "Убрать сорняки", points = 2, skillId = "skill25"),
//                    SubSkill(id = "subSkill107", name = "Удобрить газон", points = 2, skillId = "skill25")
//                )
//            ),
//            "skill26" to Skill(
//                id = "skill26",
//                name = "Компостирование",
//                categoryId = "category4",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill108", name = "Создать компостную кучу", points = 3, skillId = "skill26"),
//                    SubSkill(id = "subSkill109", name = "Перемешать компост", points = 2, skillId = "skill26"),
//                    SubSkill(id = "subSkill110", name = "Добавить органические отходы", points = 1, skillId = "skill26"),
//                    SubSkill(id = "subSkill111", name = "Использовать компост", points = 2, skillId = "skill26")
//                )
//            ),
//            "skill27" to Skill(
//                id = "skill27",
//                name = "Декоративный сад",
//                categoryId = "category4",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill112", name = "Создать клумбу", points = 3, skillId = "skill27"),
//                    SubSkill(id = "subSkill113", name = "Посадить кустарники", points = 2, skillId = "skill27"),
//                    SubSkill(id = "subSkill114", name = "Установить садовые фигуры", points = 2, skillId = "skill27"),
//                    SubSkill(id = "subSkill115", name = "Украсить дорожки", points = 2, skillId = "skill27")
//                )
//            ),
//
//            // Ремонт (6 навыков)
//            "skill7" to Skill(
//                id = "skill7",
//                name = "Домашний ремонт",
//                categoryId = "category5",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill31", name = "Повесить полку", points = 3, skillId = "skill7"),
//                    SubSkill(id = "subSkill32", name = "Покрасить стену", points = 2, skillId = "skill7"),
//                    SubSkill(id = "subSkill33", name = "Собрать мебель", points = 3, skillId = "skill7"),
//                    SubSkill(id = "subSkill34", name = "Починить кран", points = 3, skillId = "skill7")
//                )
//            ),
//            "skill28" to Skill(
//                id = "skill28",
//                name = "Электрика",
//                categoryId = "category5",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill116", name = "Заменить лампочку", points = 1, skillId = "skill28"),
//                    SubSkill(id = "subSkill117", name = "Установить розетку", points = 3, skillId = "skill28"),
//                    SubSkill(id = "subSkill118", name = "Починить выключатель", points = 2, skillId = "skill28"),
//                    SubSkill(id = "subSkill119", name = "Подключить светильник", points = 3, skillId = "skill28")
//                )
//            ),
//            "skill29" to Skill(
//                id = "skill29",
//                name = "Сантехника",
//                categoryId = "category5",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill120", name = "Прочистить засор", points = 2, skillId = "skill29"),
//                    SubSkill(id = "subSkill121", name = "Заменить прокладку в кране", points = 2, skillId = "skill29"),
//                    SubSkill(id = "subSkill122", name = "Установить смеситель", points = 3, skillId = "skill29"),
//                    SubSkill(id = "subSkill123", name = "Починить унитаз", points = 3, skillId = "skill29")
//                )
//            ),
//            "skill30" to Skill(
//                id = "skill30",
//                name = "Малярные работы",
//                categoryId = "category5",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill124", name = "Покрасить потолок", points = 3, skillId = "skill30"),
//                    SubSkill(id = "subSkill125", name = "Поклеить обои", points = 3, skillId = "skill30"),
//                    SubSkill(id = "subSkill126", name = "Зашпаклевать стену", points = 2, skillId = "skill30"),
//                    SubSkill(id = "subSkill127", name = "Покрасить дверь", points = 2, skillId = "skill30")
//                )
//            ),
//            "skill31" to Skill(
//                id = "skill31",
//                name = "Работа с деревом",
//                categoryId = "category5",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill128", name = "Сделать деревянную полку", points = 3, skillId = "skill31"),
//                    SubSkill(id = "subSkill129", name = "Отшлифовать доску", points = 2, skillId = "skill31"),
//                    SubSkill(id = "subSkill130", name = "Покрыть лаком дерево", points = 2, skillId = "skill31"),
//                    SubSkill(id = "subSkill131", name = "Сколотить ящик", points = 3, skillId = "skill31")
//                )
//            ),
//            "skill32" to Skill(
//                id = "skill32",
//                name = "Ремонт мебели",
//                categoryId = "category5",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill132", name = "Починить стул", points = 2, skillId = "skill32"),
//                    SubSkill(id = "subSkill133", name = "Перетянуть диван", points = 3, skillId = "skill32"),
//                    SubSkill(id = "subSkill134", name = "Заменить ножку стола", points = 2, skillId = "skill32"),
//                    SubSkill(id = "subSkill135", name = "Починить дверцу шкафа", points = 2, skillId = "skill32")
//                )
//            ),
//
//            // Семейные игры (6 навыков)
//            "skill8" to Skill(
//                id = "skill8",
//                name = "Настольные игры",
//                categoryId = "category6",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill35", name = "Сыграть в Монополию", points = 2, skillId = "skill8"),
//                    SubSkill(id = "subSkill36", name = "Сыграть в Шашки", points = 1, skillId = "skill8"),
//                    SubSkill(id = "subSkill37", name = "Сыграть в Шахматы", points = 2, skillId = "skill8"),
//                    SubSkill(id = "subSkill38", name = "Сыграть в Скрэббл", points = 2, skillId = "skill8")
//                )
//            ),
//            "skill9" to Skill(
//                id = "skill9",
//                name = "Активные игры",
//                categoryId = "category6",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill39", name = "Сыграть в прятки", points = 2, skillId = "skill9"),
//                    SubSkill(id = "subSkill40", name = "Сыграть в твистер", points = 2, skillId = "skill9"),
//                    SubSkill(id = "subSkill41", name = "Сыграть в футбол", points = 3, skillId = "skill9"),
//                    SubSkill(id = "subSkill42", name = "Сыграть в бадминтон", points = 2, skillId = "skill9")
//                )
//            ),
//            "skill33" to Skill(
//                id = "skill33",
//                name = "Карточные игры",
//                categoryId = "category6",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill136", name = "Сыграть в Уно", points = 2, skillId = "skill33"),
//                    SubSkill(id = "subSkill137", name = "Сыграть в Дурака", points = 1, skillId = "skill33"),
//                    SubSkill(id = "subSkill138", name = "Сыграть в Покер", points = 3, skillId = "skill33"),
//                    SubSkill(id = "subSkill139", name = "Сыграть в Бридж", points = 3, skillId = "skill33")
//                )
//            ),
//            "skill34" to Skill(
//                id = "skill34",
//                name = "Игры на улице",
//                categoryId = "category6",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill140", name = "Сыграть в догонялки", points = 2, skillId = "skill34"),
//                    SubSkill(id = "subSkill141", name = "Сыграть в классики", points = 1, skillId = "skill34"),
//                    SubSkill(id = "subSkill142", name = "Сыграть в скакалку", points = 2, skillId = "skill34"),
//                    SubSkill(id = "subSkill143", name = "Сыграть в резиночки", points = 2, skillId = "skill34")
//                )
//            ),
//            "skill35" to Skill(
//                id = "skill35",
//                name = "Командные игры",
//                categoryId = "category6",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill144", name = "Сыграть в волейбол", points = 3, skillId = "skill35"),
//                    SubSkill(id = "subSkill145", name = "Сыграть в эстафету", points = 2, skillId = "skill35"),
//                    SubSkill(id = "subSkill146", name = "Сыграть в перетягивание каната", points = 2, skillId = "skill35"),
//                    SubSkill(id = "subSkill147", name = "Сыграть в баскетбол", points = 3, skillId = "skill35")
//                )
//            ),
//            "skill36" to Skill(
//                id = "skill36",
//                name = "Логические игры",
//                categoryId = "category6",
//                subSkills = listOf(
//                    SubSkill(id = "subSkill148", name = "Собрать пазл", points = 2, skillId = "skill36"),
//                    SubSkill(id = "subSkill149", name = "Решить головоломку", points = 3, skillId = "skill36"),
//                    SubSkill(id = "subSkill150", name = "Сыграть в судоку", points = 2, skillId = "skill36"),
//                    SubSkill(id = "subSkill151", name = "Сыграть в крестики-нолики", points = 1, skillId = "skill36")
//                )
//            )
//        )
//
//// Добавление категорий
//        categories.forEach { (id, name) ->
//            val categoryData = mapOf(
//                "id" to id,
//                "name" to name
//            )
//            firebaseFirestore.collection("categories").document(id).set(categoryData)
//                .addOnSuccessListener { println("Добавлена категория: $name") }
//                .addOnFailureListener { e -> println("Ошибка: ${e.message}") }
//        }
//
//// Добавление навыков и поднавыков
//        skills.forEach { (id, skill) ->
//            val skillData = mapOf(
//                "id" to id,
//                "name" to skill.name,
//                "categoryId" to skill.categoryId
//            )
//            firebaseFirestore.collection("skills").document(id).set(skillData)
//                .addOnSuccessListener { println("Добавлен навык: ${skill.name}") }
//                .addOnFailureListener { e -> println("Ошибка: ${e.message}") }
//
//            skill.subSkills?.forEach { subSkill ->
//                val subSkillData = mapOf(
//                    "id" to UUID.randomUUID().toString(),
//                    "name" to subSkill.name,
//                    "points" to subSkill.points,
//                    "skillId" to id
//                )
//                firebaseFirestore.collection("subSkills").add(subSkillData)
//                    .addOnSuccessListener { println("Добавлен поднавык: ${subSkill.name}") }
//                    .addOnFailureListener { e -> println("Ошибка: ${e.message}") }
//            }
//        }
    }

    override fun sendPasswordResetEmail(
        email: String,
        onComplete: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete()
                } else {
                    onFailure(task.exception ?: Exception("Failed to send password reset email"))
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

}