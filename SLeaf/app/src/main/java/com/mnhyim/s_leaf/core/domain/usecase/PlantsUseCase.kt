package com.mnhyim.s_leaf.core.domain.usecase

import com.mnhyim.s_leaf.core.domain.model.Plant
import kotlinx.coroutines.flow.Flow

interface PlantsUseCase {

    fun getAllPlants(): Flow<List<Plant>>
    fun getPlant(): Flow<List<Plant>>
    fun uploadImage(image: String): Flow<Plant>
}