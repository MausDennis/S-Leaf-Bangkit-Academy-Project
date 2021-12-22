package com.mnhyim.s_leaf

import android.app.Application
import com.mnhyim.s_leaf.core.di.databaseModule
import com.mnhyim.s_leaf.core.di.networkModule
import com.mnhyim.s_leaf.core.di.repositoryModule
import com.mnhyim.s_leaf.di.useCaseModule
import com.mnhyim.s_leaf.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}