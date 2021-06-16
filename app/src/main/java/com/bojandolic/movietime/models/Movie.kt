package com.bojandolic.movietime.models

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id")
    val movieId: Long,

    @SerializedName("title")
    val title: String,

    @SerializedName("overview")
    val description: String,

    @SerializedName("vote_average")
    val vote_avg: Float,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("adult")
    val adult: Boolean,

    @SerializedName("poster_path")
    val poster: String
    )
