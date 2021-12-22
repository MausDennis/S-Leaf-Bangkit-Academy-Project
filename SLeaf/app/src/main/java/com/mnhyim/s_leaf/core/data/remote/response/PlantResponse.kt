package com.mnhyim.s_leaf.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class PlantResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("class_name")
    val className: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("desc")
    val desc: String,

    @field:SerializedName("img_urls")
    val imageURL: List<String>,

    @field:SerializedName("scientific_name")
    val scientificName: String,

    @field:SerializedName("score")
    val score: String
)