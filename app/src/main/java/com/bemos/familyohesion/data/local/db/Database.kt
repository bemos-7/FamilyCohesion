package com.bemos.familyohesion.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bemos.familyohesion.data.local.dao.CategoryDao
import com.bemos.familyohesion.data.local.dao.SkillDao
import com.bemos.familyohesion.data.local.dao.SubSkillDao
import com.bemos.familyohesion.data.local.entities.CategoryEntity
import com.bemos.familyohesion.data.local.entities.SkillEntity
import com.bemos.familyohesion.data.local.entities.SubSkillEntity

@Database(
    entities = [
        CategoryEntity::class,
        SkillEntity::class,
        SubSkillEntity::class
    ],
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract fun categoryDao() : CategoryDao
    abstract fun skillDao() : SkillDao
    abstract fun subSkillDao() : SubSkillDao
}