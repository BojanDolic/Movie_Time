package com.bojandolic.movietime.retrofit

import androidx.lifecycle.LiveData
import com.bojandolic.movietime.models.Movie
import com.bojandolic.movietime.models.MovieResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAPI {

    @GET("{category}/top_rated")
    suspend fun getTopRatedMoviesShows(
        @Path("category") category: String,
        @Query("api_key") key: String): Response<MovieResponse>

    @GET("search/{category}")
    suspend fun searchMovies(
        @Path("category") category: String,
        @Query("api_key") key: String,
        @Query("query") search: String
    ): Response<MovieResponse>

}