package com.bojandolic.movietime.ui.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.bojandolic.movietime.models.Movie
import com.bojandolic.movietime.models.MovieResponse
import com.bojandolic.movietime.repository.Repository
import com.bojandolic.movietime.utilities.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class MainFragmentViewModel @Inject constructor(
    val movieRepository: Repository
)  : ViewModel() {

    var allMovies: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()
    val movies get() = allMovies

    var data: LiveData<Resource<MovieResponse>> = MutableLiveData()
    lateinit var observer: Observer<Resource<MovieResponse>>

    private var isSearching = false

    init {
        observer = Observer<Resource<MovieResponse>> {
            allMovies.value = it
            Log.d("TAG", "getTopRatedMovies: POZVANA FUNKCIJA UPDATE\n\n ${data.value?.data?.movies?.get(0)}")
        }
    }


    fun getTopRatedMovies() {
        viewModelScope.launch {
            val data = movieRepository.getTopRatedMovies()
            data.observeForever(observer)
            Log.d("TAG", "getTopRatedMovies: POZVANA FUNKCIJA ZA FILMOVE")
        }
    }

    private fun searchMovieShows(query: String) {
        viewModelScope.launch {
            val data = movieRepository.searchMovies(query)

            /*observer = Observer<Resource<MovieResponse>> {
                allMovies.value = it
                Log.d("TAG", "getTopRatedMovies: POZVANA FUNKCIJA ZA SEARCH\n\n ${data.value?.data?.movies?.get(0)}")
            }*/
            data.observeForever(observer)
            Log.d("TAG", "getTopRatedMovies: POZVANA FUNKCIJA ZA SEARCH\n\n ${data.value?.message}")
        }
    }

    fun searchForMoviesShows(query: String) {
        Log.d("TAG", "searchForMoviesShows: $query")
        if(query.length >= 3) {
            searchMovieShows(query)
            isSearching = true
        }
        else if(query.length <= 2 && isSearching){
            getTopRatedMovies()
            isSearching = false
        }
    }



    override fun onCleared() {
        data.removeObserver(observer)
        super.onCleared()
    }

}