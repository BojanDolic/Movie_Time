package com.bojandolic.movietime.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {

    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

    val retrofitBuilder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiInterface: MoviesAPI by lazy {
        retrofitBuilder.create(MoviesAPI::class.java)
    }
}