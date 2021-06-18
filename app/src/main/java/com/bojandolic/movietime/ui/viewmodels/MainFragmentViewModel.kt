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
    val movieRepository: Repository
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
            var searchQuery = pair.second

            if (category != null && searchQuery != null) {
                fetchMoviesAndShows(category, searchQuery)
            } else null

        }

    //MutableLiveData()
    val movies get() = allMovies

    var data: LiveData<Resource<MovieResponse>> = MutableLiveData()
    lateinit var observer: Observer<Resource<MovieResponse>>

    var isSearching = false
    private var searchQuery = ""
    val searchText get() = searchQuery



    /*init {
        observer = Observer<Resource<MovieResponse>> {
            allMovies.value = it
            Log.d(
                "TAG",
                "getTopRatedMovies: POZVANA FUNKCIJA UPDATE\n\n ${data.value?.data?.movies?.get(0)}"
            )
        }
    }*/

    fun fetchMoviesAndShows(category: String, searchQuery: String): LiveData<Resource<MovieResponse>> {
        return if(searchQuery.length <= 2)
            getTopRatedMoviesShows(category)
        else searchMovieShows(searchQuery)
    }

    fun getTopRatedMoviesShows(category: String) = movieRepository.getTopRatedMoviesShows(category)
        //viewModelScope.launch {
            /*val data = movieRepository.getTopRatedMoviesShows()
            data.observeForever(observer)
            Log.d("TAG", "getTopRatedMovies: POZVANA FUNKCIJA ZA FILMOVE")*/
        //}
    //}

    private fun searchMovieShows(query: String) = movieRepository.searchMovies(category.value ?: "movie", query)

    /*fun searchForMoviesShows(query: String) {
        searchQuery = query
        if (query.length >= 3) {
            searchMovieShows(query)
        } else if (query.length <= 2) {
            getTopRatedMoviesShows()
        }
    }*/


    override fun onCleared() {
        data.removeObserver(observer)
        super.onCleared()
    }

}