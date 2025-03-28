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

//        val skills = mapOf(
//            "Кулинария" to listOf(
//                Skill("Приготовление пиццы", listOf(
//                    SubSkill("Приготовить пеперони", 3),
//                    SubSkill("Приготовить 4 сыра", 2),
//                    SubSkill("Приготовить маргариту", 2),
//                    SubSkill("Приготовить охотничью", 1),
//                    SubSkill("Приготовить грибную пиццу", 2),
//                    SubSkill("Приготовить овощную пиццу", 1),
//                    SubSkill("Приготовить пиццу с морепродуктами", 3)
//                )),
//                Skill("Приготовление супов", listOf(
//                    SubSkill("Сварить борщ", 3),
//                    SubSkill("Сварить рассольник", 2),
//                    SubSkill("Сварить куриный суп", 1),
//                    SubSkill("Сварить крем-суп из грибов", 2),
//                    SubSkill("Сварить овощной суп", 1),
//                    SubSkill("Сварить рыбный суп", 3)
//                )),
//                Skill("Выпечка", listOf(
//                    SubSkill("Испечь пирог с яблоками", 2),
//                    SubSkill("Испечь кексы", 1),
//                    SubSkill("Испечь хлеб", 3),
//                    SubSkill("Испечь печенье", 2),
//                    SubSkill("Испечь торт", 3),
//                    SubSkill("Испечь булочки с корицей", 2)
//                )),
//                Skill("Приготовление гарниров", listOf(
//                    SubSkill("Приготовить картофельное пюре", 1),
//                    SubSkill("Приготовить овощное рагу", 2),
//                    SubSkill("Приготовить пасту карбонара", 3),
//                    SubSkill("Приготовить жареный рис с овощами", 2),
//                    SubSkill("Приготовить гречку с грибами", 1),
//                    SubSkill("Приготовить плов", 3)
//                )),
//                Skill("Приготовление завтраков", listOf(
//                    SubSkill("Приготовить омлет с сыром", 1),
//                    SubSkill("Приготовить кашу на молоке", 1),
//                    SubSkill("Сделать блинчики", 2),
//                    SubSkill("Приготовить сырники", 2),
//                    SubSkill("Приготовить яйца Бенедикт", 3),
//                    SubSkill("Приготовить смузи боул", 2)
//                )),
//                Skill("Соусы и приправы", listOf(
//                    SubSkill("Сделать домашний майонез", 3),
//                    SubSkill("Приготовить классический соус бешамель", 2),
//                    SubSkill("Приготовить томатный соус", 2),
//                    SubSkill("Сделать песто", 2),
//                    SubSkill("Приготовить грибной соус", 3),
//                    SubSkill("Сделать заправку для салата с горчицей и медом", 1)
//                )),
//                Skill("Десерты", listOf(
//                    SubSkill("Приготовить панна-котту", 3),
//                    SubSkill("Сделать шоколадный мусс", 2),
//                    SubSkill("Приготовить тирамису", 3),
//                    SubSkill("Сделать фруктовый салат", 1),
//                    SubSkill("Приготовить желе из сока", 1),
//                    SubSkill("Испечь эклеры", 3)
//                )),
//                Skill("Заготовки", listOf(
//                    SubSkill("Засолить огурцы", 2),
//                    SubSkill("Приготовить варенье из ягод", 3),
//                    SubSkill("Замариновать грибы", 3),
//                    SubSkill("Приготовить квашеную капусту", 2),
//                    SubSkill("Заморозить зелень", 1),
//                    SubSkill("Приготовить домашний кетчуп", 3)
//                )),
//                Skill("Приготовление напитков", listOf(
//                    SubSkill("Сделать лимонад", 1),
//                    SubSkill("Приготовить какао", 1),
//                    SubSkill("Заварить чай с имбирем и лимоном", 1),
//                    SubSkill("Сделать молочный коктейль", 2),
//                    SubSkill("Приготовить глинтвейн", 3),
//                    SubSkill("Приготовить холодный кофе", 2)
//                ))
//            ),
//            "Рисование" to listOf(
//                Skill("Основы рисования", listOf(
//                    SubSkill("Нарисовать домик", 1),
//                    SubSkill("Нарисовать дерево", 1),
//                    SubSkill("Нарисовать солнце", 1),
//                    SubSkill("Нарисовать радугу", 1)
//                )),
//                Skill("Цвета и тени", listOf(
//                    SubSkill("Закрасить круг одним цветом", 1),
//                    SubSkill("Сделать градиент от темного к светлому", 2),
//                    SubSkill("Нарисовать тень от объекта", 2),
//                    SubSkill("Смешать два цвета", 2)
//                )),
//                Skill("Узор", listOf(
//                    SubSkill("Нарисовать простой орнамент", 1),
//                    SubSkill("Нарисовать полоски и точки", 1),
//                    SubSkill("Нарисовать шахматный узор", 2),
//                    SubSkill("Нарисовать спираль", 2)
//                )),
//                Skill("Пейзаж", listOf(
//                    SubSkill("Нарисовать горы", 2),
//                    SubSkill("Изобразить реку", 2),
//                    SubSkill("Нарисовать облака", 1),
//                    SubSkill("Изобразить закат", 3)
//                )),
//                Skill("Портрет", listOf(
//                    SubSkill("Нарисовать глаза", 2),
//                    SubSkill("Нарисовать нос", 2),
//                    SubSkill("Нарисовать рот", 2),
//                    SubSkill("Нарисовать ухо", 2)
//                )),
//                Skill("Рисование животных", listOf(
//                    SubSkill("Нарисовать котенка", 2),
//                    SubSkill("Изобразить птицу", 2),
//                    SubSkill("Нарисовать рыбу", 1),
//                    SubSkill("Изобразить лошадь", 3)
//                )),
//                Skill("Перспектива", listOf(
//                    SubSkill("Нарисовать дорогу в перспективе", 3),
//                    SubSkill("Изобразить здание в 3D", 3),
//                    SubSkill("Нарисовать куб", 2),
//                    SubSkill("Нарисовать цилиндр", 2)
//                )),
//                Skill("Инструменты для рисования", listOf(
//                    SubSkill("Использовать кисть для акварели", 2),
//                    SubSkill("Попробовать рисунок карандашом", 1),
//                    SubSkill("Использовать пастель", 2),
//                    SubSkill("Нарисовать маркерами", 2)
//                )),
//                Skill("Фантазийные рисунки", listOf(
//                    SubSkill("Нарисовать дракона", 3),
//                    SubSkill("Изобразить сказочный замок", 3),
//                    SubSkill("Нарисовать космос", 2),
//                    SubSkill("Изобразить вымышленное животное", 2)
//                ))
//            )
//        )
//
//
//        skills.forEach { (category, skillList) ->
//            val categoryDoc = firebaseFirestore.collection("skills").document(category)
//
//            skillList.forEach { skill ->
//                val skillDoc = categoryDoc.collection("subSkills").document(skill.name)
//                skill.subSkills.forEach { subSkill ->
//                    val subSkillData = mapOf(
//                        "name" to subSkill.name,
//                        "points" to subSkill.points
//                    )
//                    skillDoc.collection("subSubSkills").add(subSkillData)
//                        .addOnSuccessListener { Log.d("FirebaseSkills","Successfully added: ${subSkill.name}") }
//                        .addOnFailureListener { e -> println("Error adding subSkill: ${e.message}") }
//                }
//            }
//        }
//        val skills = mapOf(
//            "Кулинария" to listOf(
//                Skill("Приготовление пиццы", listOf(
//                    SubSkill("Приготовить пеперони", 3),
//                    SubSkill("Приготовить 4 сыра", 2),
//                    SubSkill("Приготовить маргариту", 2),
//                    SubSkill("Приготовить охотничью", 1),
//                    SubSkill("Приготовить грибную пиццу", 2),
//                    SubSkill("Приготовить овощную пиццу", 1),
//                    SubSkill("Приготовить пиццу с морепродуктами", 3)
//                )),
//                Skill("Приготовление супов", listOf(
//                    SubSkill("Сварить борщ", 3),
//                    SubSkill("Сварить рассольник", 2),
//                    SubSkill("Сварить куриный суп", 1),
//                    SubSkill("Сварить крем-суп из грибов", 2),
//                    SubSkill("Сварить овощной суп", 1),
//                    SubSkill("Сварить рыбный суп", 3)
//                ))
//            ),
//            "Рисование" to listOf(
//                Skill("Основы рисования", listOf(
//                    SubSkill("Нарисовать домик", 1),
//                    SubSkill("Нарисовать дерево", 1),
//                    SubSkill("Нарисовать солнце", 1),
//                    SubSkill("Нарисовать радугу", 1)
//                )),
//                Skill("Цвета и тени", listOf(
//                    SubSkill("Закрасить круг одним цветом", 1),
//                    SubSkill("Сделать градиент от темного к светлому", 2),
//                    SubSkill("Нарисовать тень от объекта", 2),
//                    SubSkill("Смешать два цвета", 2)
//                ))
//            )
//        )
//
//        skills.forEach { (category, skillList) ->
//            val categoryRef = firebaseFirestore.collection("skills").document(category)
//
//            skillList.forEach { skill ->
//                val skillData = mapOf(
//                    "name" to skill.name,
//                    "subSkills" to skill.subSkills.map { subSkill ->
//                        mapOf("name" to subSkill.name, "level" to subSkill.points)
//                    }
//                )
//
//                categoryRef.collection("skills").add(skillData)
//                    .addOnSuccessListener { println("Добавлен навык: ${skill.name}") }
//                    .addOnFailureListener { e -> println("Ошибка: ${e.message}") }
//            }
//        }

//        val categories = mapOf(
//            "category1" to Category(id = "category1", name = "Кулинария"),
//            "category2" to Category(id = "category2", name = "Рисование")
//        )
//
//        val skills = mapOf(
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
//            skill.subSkills.forEach { subSkill ->
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