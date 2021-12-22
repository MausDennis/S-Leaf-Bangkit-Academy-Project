package com.mnhyim.s_leaf.core.data

import com.mnhyim.s_leaf.core.data.local.LocalDataSource
import com.mnhyim.s_leaf.core.data.remote.RemoteDataSource
import com.mnhyim.s_leaf.core.domain.model.Plant
import com.mnhyim.s_leaf.core.domain.repository.IPlantsRepository
import com.mnhyim.s_leaf.utils.DataMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PlantsRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IPlantsRepository {

    /* Local stuff */
    override fun getAllFavorites(): Flow<List<Plant>> {
        return localDataSource.getAllFavorite().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun addFavorite(plant: Plant) {
        CoroutineScope(Dispatchers.Main).launch {
            val plantEntity = DataMapper.mapDomainToEntity(plant)
            localDataSource.addFavorite(plantEntity)
        }
    }

    override fun deleteByName(name: String) {
        CoroutineScope(Dispatchers.Main).launch {
            localDataSource.deleteByName(name)
        }
    }

    override fun checkFavorite(name: String): Flow<Int> {
        return localDataSource.checkFavorite(name)
    }

    /* Remote stuff */
    override fun getAllPlants(): Flow<List<Plant>> {
        return remoteDataSource.getAllPlants().map {
            DataMapper.mapResponseToDomain(it)
        }
    }

    override fun getPlant(): Flow<List<Plant>> {
        return remoteDataSource.getPlant().map {
            DataMapper.mapResponseToDomain(it)
        }
    }

    override fun uploadImage(image: String): Flow<Plant> {
        return remoteDataSource.uploadImage(image).map {
            DataMapper.mapResponseToDomain(it)
        }
    }
}