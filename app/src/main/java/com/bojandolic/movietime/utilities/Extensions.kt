package com.bojandolic.movietime.utilities

import android.widget.ImageView
import coil.Coil
import coil.load
import coil.util.CoilUtils
import com.bojandolic.movietime.retrofit.Client

fun ImageView.loadMovieUrl(url: String) {
    this.load(Client.IMAGE_BASE_URL + url)
}