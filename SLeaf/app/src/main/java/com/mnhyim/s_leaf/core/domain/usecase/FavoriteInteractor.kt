package com.mnhyim.s_leaf.core.domain.usecase

import com.mnhyim.s_leaf.core.domain.model.Plant
import com.mnhyim.s_leaf.core.domain.repository.IPlantsRepository
import kotlinx.coroutines.flow.Flow

class FavoriteInteractor(private val favoriteRepository: IPlantsRepository) : FavoriteUseCase {

    override fun getAllFavorites(): Flow<List<Plant>> = favoriteRepository.getAllFavorites()

    override fun addFavorite(plant: Plant) {
        favoriteRepository.addFavorite(plant)
    }

    override fun checkFavorite(name: String): Flow<Int> = favoriteRepository.checkFavorite(name)

    override fun deleteByName(name: String) {
        favoriteRepository.deleteByName(name)
    }
}