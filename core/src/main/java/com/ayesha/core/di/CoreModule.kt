package com.ayesha.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class IoDispatcher

     @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO


    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

}