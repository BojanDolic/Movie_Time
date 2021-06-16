package com.bojandolic.movietime.di

import com.bojandolic.movietime.repository.Repository
import com.bojandolic.movietime.retrofit.MoviesAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(moviesAPI: MoviesAPI): Repository {
        return Repository(moviesAPI)
    }
}