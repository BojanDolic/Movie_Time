package com.bojandolic.movietime.di

import com.bojandolic.movietime.retrofit.Client
import com.bojandolic.movietime.retrofit.MoviesAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideMovieService(): MoviesAPI {
        return Client.apiInterface
    }

}