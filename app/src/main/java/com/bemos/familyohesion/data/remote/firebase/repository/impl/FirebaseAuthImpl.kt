package com.bemos.familyohesion.data.remote.firebase.repository.impl

import android.util.Log
import com.bemos.familyohesion.domain.models.Family
import com.bemos.familyohesion.domain.models.Skill
import com.bemos.familyohesion.domain.models.SubSkill
import com.bemos.familyohesion.domain.models.UserAuth
import com.bemos.familyohesion.domain.repositories.FirebaseAuthRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
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

    override fun signUp(userAuth: UserAuth, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(userAuth.email, userAuth.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = firebaseAuth.currentUser?.uid ?: return@addOnCompleteListener
                    val familyId = UUID.randomUUID().toString()

                    val newFamily = Family(
                        familyId = familyId,
                        adminId = userId,
                        members = listOf(
                            mapOf(
                                "name" to userAuth.name,
                                "points" to 0,
                                "relation" to userAuth.userRole
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
    }

}