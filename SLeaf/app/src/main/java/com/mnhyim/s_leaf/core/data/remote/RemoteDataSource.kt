package com.mnhyim.s_leaf.core.data.remote

import android.util.Log
import com.mnhyim.s_leaf.core.data.remote.api.ApiService
import com.mnhyim.s_leaf.core.data.remote.response.PlantResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    fun getAllPlants(): Flow<List<PlantResponse>> {
        return flow {
            try {
                val response = apiService.getAllPlants()
                val dataArray = response
                if (dataArray.isNotEmpty()) {
                    emit(dataArray)
                } else {
                    Log.e("RemoteDataSource", "empty")
                }
            } catch (e: Exception) {
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getPlant(): Flow<List<PlantResponse>> {
        return flow {
            try {
                val response = apiService.getPlant()
                emit(response)
            } catch (e: Exception) {
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun uploadImage(image: String): Flow<PlantResponse> {
        return flow {
            try {
                val response = apiService.uploadImage(image)
                emit(response)
            } catch (e: Exception) {
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}