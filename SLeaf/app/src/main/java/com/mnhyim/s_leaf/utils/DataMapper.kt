package com.mnhyim.s_leaf.utils

import com.mnhyim.s_leaf.core.data.local.entity.PlantEntity
import com.mnhyim.s_leaf.core.data.remote.response.PlantResponse
import com.mnhyim.s_leaf.core.domain.model.Plant
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

object DataMapper {
    fun mapEntitiesToDomain(input: List<PlantEntity>): List<Plant> =
        input.map {
            Plant(
                id = it.id,
                desc = it.desc,
                className = it.className,
                name = it.name,
                scientificName = it.scientificName,
                imageURL = it.imageURL,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Plant) = PlantEntity(
        desc = input.desc,
        className = input.className,
        name = input.name,
        scientificName = input.scientificName,
        imageURL = input.imageURL,
        isFavorite = input.isFavorite
    )

    fun mapResponseToDomain(input: List<PlantResponse>): List<Plant> =
        input.map {
            Plant(
                id = it.id,
                desc = it.desc,
                className = it.className,
                name = it.name,
                scientificName = it.scientificName,
                imageURL = it.imageURL,
                isFavorite = false,
                score = it.score
            )
        }

    fun mapResponseToDomain(input: PlantResponse) =
        Plant(
            id = input.id,
            desc = input.desc,
            className = input.className,
            name = input.name,
            scientificName = input.scientificName,
            imageURL = input.imageURL,
            isFavorite = false,
            score = input.score
        )

    fun mapDomainToCarouselItem(input: List<Plant>): List<CarouselItem> =
        input.map {
            CarouselItem(
                imageUrl = it.imageURL[0],
                caption = it.className
            )
        }

}