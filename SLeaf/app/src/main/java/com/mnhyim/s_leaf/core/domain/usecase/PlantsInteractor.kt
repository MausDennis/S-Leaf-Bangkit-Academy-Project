package com.mnhyim.s_leaf.core.domain.usecase

import com.mnhyim.s_leaf.core.domain.model.Plant
import com.mnhyim.s_leaf.core.domain.repository.IPlantsRepository
import kotlinx.coroutines.flow.Flow

class PlantsInteractor(private val favoriteRepository: IPlantsRepository) : PlantsUseCase {

    override fun getAllPlants(): Flow<List<Plant>> {
        return favoriteRepository.getAllPlants()
    }

    override fun getPlant(): Flow<List<Plant>> {
        return favoriteRepository.getPlant()
    }

    override fun uploadImage(image: String): Flow<Plant> {
        return favoriteRepository.uploadImage(image)
    }
}