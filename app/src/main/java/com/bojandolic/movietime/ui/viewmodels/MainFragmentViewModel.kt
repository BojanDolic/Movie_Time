package com.bojandolic.movietime.ui.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.bojandolic.movietime.models.Movie
import com.bojandolic.movietime.models.MovieResponse
import com.bojandolic.movietime.repository.Repository
import com.bojandolic.movietime.utilities.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
public class MainFragmentViewModel @Inject constructor(
    private val movieRepository: Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val category: MutableLiveData<String> = MutableLiveData("movie")
    val searchQuery2: MutableLiveData<String> = MutableLiveData("")

    private val combinedQuery = MediatorLiveData<Pair<String?, String?>>().apply {
        addSource(category) {
            value = Pair(it, searchQuery2.value)
        }

        addSource(searchQuery2) {
            value = Pair(category.value, it)
        }
    }


    var allMovies: LiveData<Resource<MovieResponse>> =
        Transformations.switchMap(combinedQuery) { pair ->

            val category = pair.first
            val searchQuery = pair.second

            if (category != null && searchQuery != null) {
                fetchMoviesAndShows(category, searchQuery)
            } else null

        }

    val movies get() = allMovies
    var isSearching = false


    private fun fetchMoviesAndShows(category: String, searchQuery: String): LiveData<Resource<MovieResponse>> {
        return if(searchQuery.length <= 2)
            getTopRatedMoviesShows(category)
        else searchMovieShows(category, searchQuery)
    }

    private fun getTopRatedMoviesShows(category: String) = movieRepository.getTopRatedMoviesShows(category)

    private fun searchMovieShows(category: String, query: String): LiveData<Resource<MovieResponse>> {
        return movieRepository.searchMovies(category, query)
    }

}