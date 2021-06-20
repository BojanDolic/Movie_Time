package com.bojandolic.movietime.utilities

import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import coil.Coil
import coil.load
import coil.util.CoilUtils
import com.bojandolic.movietime.R
import com.bojandolic.movietime.models.Movie
import com.bojandolic.movietime.retrofit.Client
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

/**
 * Extension function used to load images
 *
 * @param url section of URL which determines which image is going to be loaded
 * @param imageType Type of image to request from server.
 * @see Movie.IMAGE
 */
fun ImageView.loadMovieUrl(url: String?, imageType: String) {
    this.load(Client.IMAGE_BASE_URL.replace("w500", imageType) + (url ?: "")) {
        error(R.drawable.search_icon)
    }
}

fun SearchView.onTextChangeWaitListener2(callback: () -> String) {

    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {

            return false
        }
    })

}


/**
 * Extension function used on SearchView to get search query
 * It uses callbackFlow with .debounce() to wait n seconds before publishing result
 */
@ExperimentalCoroutinesApi
fun SearchView.onTextChangeWaitListener() = callbackFlow {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            if (newText != null && newText.isNotEmpty())
                trySend(newText)
            else trySend("")
            return true
        }
    })


    setOnCloseListener {
        trySend("")
        true
    }

    awaitClose {
        setOnQueryTextListener(null)
    }
}
