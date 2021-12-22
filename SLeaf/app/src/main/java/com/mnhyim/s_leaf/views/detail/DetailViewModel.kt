package com.mnhyim.s_leaf.views.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mnhyim.s_leaf.core.domain.model.Plant
import com.mnhyim.s_leaf.core.domain.usecase.FavoriteUseCase

class DetailViewModel(private val favoriteUseCase: FavoriteUseCase) : ViewModel() {

    fun checkFavorite(name: String): LiveData<Int> {
        return favoriteUseCase.checkFavorite(name).asLiveData()
    }

    fun addFavorite(plant: Plant) {
        favoriteUseCase.addFavorite(plant)
    }

    fun removeFavorite(name: String) {
        favoriteUseCase.deleteByName(name)
    }
}