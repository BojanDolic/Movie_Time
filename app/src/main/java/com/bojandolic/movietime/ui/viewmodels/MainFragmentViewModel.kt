package com.bojandolic.movietime.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    var allMovies: LiveData<Resource<MovieResponse>> = MutableLiveData()
    val movies get() = allMovies


    fun getTopRatedMovies() {
        viewModelScope.launch {
            allMovies = movieRepository.getTopRatedMovies()
            Log.d("TAG", "getTopRatedMovies: POZVANA FUNKCIJA")
        }
    }

}