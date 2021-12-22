package com.mnhyim.s_leaf.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mnhyim.s_leaf.core.data.local.entity.PlantEntity
import com.mnhyim.s_leaf.utils.StringListConverters

@Database(entities = [PlantEntity::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverters::class)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao
}