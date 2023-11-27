package com.example.medibanknewsapp.di

import android.app.Application
import com.example.medibanknewsapp.AppCoroutineDispatchers
import com.example.medibanknewsapp.data.remote.api.ApiInterface
import com.example.medibanknewsapp.util.Constants.Companion.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Module
    @InstallIn(SingletonComponent::class)
    object NetworkModule {

        @Provides
        @JvmStatic
        @Singleton
        internal fun provideCache(application: Application): Cache {
            val cacheSize = 10 * 1024 * 1024.toLong() // 10 MB
            val httpCacheDirectory = File(application.cacheDir, "http-cache")
            return Cache(httpCacheDirectory, cacheSize)
        }


        @Singleton
        @Provides
        fun provideHttpInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        @Singleton
        @Provides
        fun provideOkHttp(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()
        }

        @Singleton
        @Provides
        fun provideGsonBuilder(): Gson {
            return GsonBuilder().create()
        }

        @Singleton
        @Provides
        fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit.Builder {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)

        }

        @Singleton
        @Provides
        fun provideMediBankService(retrofit: Retrofit.Builder): ApiInterface {
            return retrofit.build().create(ApiInterface::class.java)
        }

        @Provides
        fun provideDispatcher(): AppCoroutineDispatchers {
            return AppCoroutineDispatchers()
        }

    }
}