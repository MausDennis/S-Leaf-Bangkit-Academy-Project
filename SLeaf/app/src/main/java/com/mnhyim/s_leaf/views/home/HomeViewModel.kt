package com.mnhyim.s_leaf.views.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mnhyim.s_leaf.core.domain.model.Plant
import com.mnhyim.s_leaf.core.domain.usecase.PlantsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class HomeViewModel(private val plantsUseCase: PlantsUseCase) : ViewModel() {
    val listPlants: LiveData<List<Plant>> = plantsUseCase.getAllPlants().asLiveData()

    fun getPlant() = plantsUseCase.getPlant().asLiveData()

}