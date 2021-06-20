package com.bojandolic.movietime.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    @SerializedName("id")
    val movieId: Long,

    @SerializedName("title", alternate = ["name"])
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
    val poster: String,

    @SerializedName("backdrop_path")
    val backdropImage: String
    ) : Parcelable {

    companion object IMAGE {
        const val IMAGE_ORIGINAL = "original"
        const val IMAGE_W500 = "w500"
        const val IMAGE_W780 = "w780"
    }

}
