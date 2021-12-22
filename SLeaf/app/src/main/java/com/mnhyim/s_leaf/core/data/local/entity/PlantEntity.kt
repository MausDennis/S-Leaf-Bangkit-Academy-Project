package com.mnhyim.s_leaf.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "favorite", indices = [Index(value = ["class_name"], unique = true)])
data class PlantEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "class_name")
    var className: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "desc")
    var desc: String,

    @ColumnInfo(name = "scientific_name")
    var scientificName: String,

    @ColumnInfo(name = "imageURL")
    var imageURL: List<String>,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)
