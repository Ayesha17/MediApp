package com.ayesha.network

import android.content.Context
import com.ayesha.network.connectivity.DefaultConnectivityObserver
import com.ayesha.network.interceptor.AppVersionHeaderInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier

const val TIMEOUT_CONNECT = 30L
const val TIMEOUT_READ = 30L
const val TIMEOUT_WRITE = 30L
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun providInterceptor() =
        HttpLoggingInterceptor().apply {
            level =
//                if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
//                else HttpLoggingInterceptor.Level.NONE
        }

    @Provides
    fun provideAppVersion() = AppVersionHeaderInterceptor()

    @Provides
    fun provideHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        appVersionHeaderInterceptor: AppVersionHeaderInterceptor
    ):OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
            .addInterceptor(appVersionHeaderInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }
    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class RetrofitData

    @RetrofitData
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://run.mocky.io/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    @Provides
    fun providesGson(): Gson {
        return Gson()
    }
    @Provides
    fun provideConnectivityObserver(@ApplicationContext context: Context)= DefaultConnectivityObserver(context)
}