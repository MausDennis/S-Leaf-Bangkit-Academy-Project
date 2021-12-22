package com.mnhyim.s_leaf.core.domain.repository

import com.mnhyim.s_leaf.core.domain.model.Plant
import kotlinx.coroutines.flow.Flow

interface IPlantsRepository {

    fun getAllFavorites(): Flow<List<Plant>>
    fun addFavorite(plant: Plant)
    fun checkFavorite(name: String): Flow<Int>
    fun deleteByName(name: String)

    fun getAllPlants(): Flow<List<Plant>>
    fun getPlant(): Flow<List<Plant>>
    fun uploadImage(image: String): Flow<Plant>
}