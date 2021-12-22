package com.mnhyim.s_leaf.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Plant(
    val id: Int = 0,
    val className: String,
    val name: String,
    val desc: String,
    val scientificName: String,
    val imageURL: List<String>,
    val isFavorite: Boolean,
    val score: String = ""
) : Parcelable