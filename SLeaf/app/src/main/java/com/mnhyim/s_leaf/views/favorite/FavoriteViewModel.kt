package com.mnhyim.s_leaf.views.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mnhyim.s_leaf.core.domain.model.Plant
import com.mnhyim.s_leaf.core.domain.usecase.FavoriteUseCase

class FavoriteViewModel(private val favoriteUseCase: FavoriteUseCase) : ViewModel() {
    val listFavorite = favoriteUseCase.getAllFavorites().asLiveData()

    fun addFavorite(plant: Plant) {
        favoriteUseCase.addFavorite(plant)
    }
}