package com.sky.data.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.sky.data.api.MoviesApi
import com.sky.data.common.CacheInterceptor
import com.sky.data.common.Constants
import com.sky.data.common.Constants.CACHE_SIZE
import com.sky.data.mappers.MovieApiResponseMapper
import com.sky.data.repositories.MovieRepositoryImpl
import com.sky.domain.repositories.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideMovieApi(@ApplicationContext context: Context): MoviesApi {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .cache(httpCache(context))
            .addInterceptor(interceptor)
            .addNetworkInterceptor(CacheInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(MoviesApi::class.java)
    }


    @Provides
    @Singleton
    fun provideMovieRepository(
        api: MoviesApi,
        mapper: dagger.Lazy<MovieApiResponseMapper>
    ): MovieRepository {
        return MovieRepositoryImpl(api, mapper)
    }

    private fun httpCache(application: Context): Cache {
        return Cache(application.applicationContext.cacheDir, CACHE_SIZE)
    }
}