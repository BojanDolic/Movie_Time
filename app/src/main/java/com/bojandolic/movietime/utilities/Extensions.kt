package com.bojandolic.movietime.utilities

import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import coil.Coil
import coil.load
import coil.util.CoilUtils
import com.bojandolic.movietime.R
import com.bojandolic.movietime.retrofit.Client
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun ImageView.loadMovieUrl(url: String?) {

    this.load(Client.IMAGE_BASE_URL + (url ?: "")) {
        error(R.drawable.search_icon)
    }
}

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
        false
    }

    awaitClose { setOnQueryTextListener(null) }
}