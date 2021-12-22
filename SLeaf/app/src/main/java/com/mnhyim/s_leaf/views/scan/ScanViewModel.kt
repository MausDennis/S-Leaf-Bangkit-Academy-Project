package com.mnhyim.s_leaf.views.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mnhyim.s_leaf.core.domain.usecase.PlantsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class ScanViewModel(private val plantsUseCase: PlantsUseCase) : ViewModel() {

    private val queryChannel = ConflatedBroadcastChannel<String>()
    fun setScanImage(image: String) {
        queryChannel.offer(image)
    }

    val scanResult = queryChannel.asFlow()
        .debounce(100)
        .distinctUntilChanged()
        .flatMapMerge {
            plantsUseCase.uploadImage(it)
        }.asLiveData()
}