package com.ayesha.mediapp.di

import android.content.Context
import com.ayesha.core.di.CoreModule
import com.ayesha.mediapp.data.database.dao.AppDatabase
import com.ayesha.mediapp.data.database.dao.UserDao
import com.ayesha.mediapp.data.remote.LocalUserDataSource
import com.ayesha.mediapp.data.remote.MedicationApiInterface
import com.ayesha.mediapp.data.remote.MedicationMapper
import com.ayesha.mediapp.data.remote.RemoteMedicationDataSource
import com.ayesha.mediapp.data.repository.MedicationRepository
import com.ayesha.mediapp.data.repository.UserRepository
import com.ayesha.mediapp.domain.GetMedicineUseCase
import com.ayesha.mediapp.domain.GetUserUseCase
import com.ayesha.mediapp.domain.UserAuthUseCase
import com.ayesha.network.NetworkModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Provides
    fun provideUserDao(
        database: AppDatabase
    ) = database.userDao()

    @Provides
    fun provideMedicationApiInterface(@NetworkModule.RetrofitData retrofit: Retrofit)=retrofit.create(MedicationApiInterface::class.java)

    @Provides
    fun provideMedicationMapper()=MedicationMapper()
    @Provides
    fun provideMedicationDataSource(medicationApiInterface: MedicationApiInterface,medicationMapper: MedicationMapper) =
        RemoteMedicationDataSource(medicationApiInterface,medicationMapper)


    @Provides
    fun provideMedicationRepository(medicationDataSource: RemoteMedicationDataSource) =
        MedicationRepository(medicationDataSource)

    @Provides
    fun provideGetMedicationUseCase(
        medicationRepository: MedicationRepository,
        @CoreModule.IoDispatcher ioDispatcher: CoroutineDispatcher
    ) =
        GetMedicineUseCase(medicationRepository, ioDispatcher)

    @Provides
    fun providelocalUserDataSource(userDao: UserDao) =
        LocalUserDataSource(userDao)


    @Provides
    fun provideUserRepository(localUserDataSource: LocalUserDataSource) =
        UserRepository(localUserDataSource)

    @Provides
    fun provideUserAuthUseCase(
        userRepository: UserRepository,
        @CoreModule.IoDispatcher ioDispatcher: CoroutineDispatcher
    ) =
        UserAuthUseCase(userRepository, ioDispatcher)

    @Provides
    fun provideGetUserUseCase(
        userRepository: UserRepository,
        @CoreModule.IoDispatcher ioDispatcher: CoroutineDispatcher
    ) =
        GetUserUseCase(userRepository, ioDispatcher)

}