package com.mnhyim.s_leaf.core.di

import androidx.room.Room
import com.mnhyim.s_leaf.core.data.PlantsRepository
import com.mnhyim.s_leaf.core.data.local.LocalDataSource
import com.mnhyim.s_leaf.core.data.local.room.FavoriteDatabase
import com.mnhyim.s_leaf.core.data.remote.RemoteDataSource
import com.mnhyim.s_leaf.core.data.remote.api.ApiService
import com.mnhyim.s_leaf.core.domain.repository.IPlantsRepository
import com.mnhyim.s_leaf.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<FavoriteDatabase>().favoriteDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            FavoriteDatabase::class.java, "Favorite.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<IPlantsRepository> { PlantsRepository(get(), get()) }
}