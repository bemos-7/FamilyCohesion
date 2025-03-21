package com.bemos.familyohesion.data.mappers

import com.bemos.familyohesion.data.local.entities.CategoryEntity
import com.bemos.familyohesion.domain.models.Category

object CategoryMapper {
    fun Category.toEntity() : CategoryEntity {
        return CategoryEntity(
            id, name
        )
    }

    fun CategoryEntity.toCategory() : Category {
        return Category(
            id, name
        )
    }
}