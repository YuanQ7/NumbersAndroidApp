package com.example.numbers.di

import com.example.numbers.network.NumbersApi
import com.example.numbers.repository.NumbersRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
import com.example.numbers.util.Constants.Companion.API_BASE_URL
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NumbersModule {

    @Provides
    @Singleton
    fun provideMovieApi(moshi: Moshi): NumbersApi {
        return Retrofit.Builder()
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(API_BASE_URL)
            .build()
            .create(NumbersApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    @Provides
    @Singleton
    fun provideRepository(numbersApi: NumbersApi): NumbersRepository {
        return NumbersRepository(numbersApi)
    }

}