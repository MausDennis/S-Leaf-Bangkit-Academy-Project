package com.mnhyim.s_leaf.core.domain.usecase

import com.mnhyim.s_leaf.core.domain.model.Plant
import kotlinx.coroutines.flow.Flow

interface FavoriteUseCase {

    fun getAllFavorites(): Flow<List<Plant>>

    fun addFavorite(plant: Plant)

    fun checkFavorite(name: String): Flow<Int>

    fun deleteByName(name: String)
}