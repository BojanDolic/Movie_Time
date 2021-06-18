package com.bojandolic.movietime.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.bojandolic.movietime.BuildConfig
import com.bojandolic.movietime.models.Movie
import com.bojandolic.movietime.retrofit.MoviesAPI
import com.bojandolic.movietime.utilities.RepositorySource
import com.bojandolic.movietime.utilities.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    val moviesApi: MoviesAPI
) : RepositorySource() {

    /**
     * Gets top rated movies or shows based on category
     *
     * @param category string which represents if API result will be movies or tv shows
     *
     * @see getRemoteResult
     */
    fun getTopRatedMoviesShows(category: String) = getRemoteData {
        getRemoteResult { moviesApi.getTopRatedMoviesShows(category, BuildConfig.API_KEY) }
    }


    /**
     * Network call to search for movie using query
     *
     * @param query movie name
     */
    fun searchMovies(category: String, query: String) = getRemoteData {
        getRemoteResult {
            moviesApi.searchMovies(category, BuildConfig.API_KEY, query)
        }
    }


    private fun <T> getRemoteData(
        networkCall: suspend () -> Resource<T>
    ): LiveData<Resource<T>> = liveData(Dispatchers.IO) {
        emit(Resource.loading())

        val response = networkCall.invoke()
        if (response.status == Resource.Status.SUCCESS)
            emit(response)
        else if (response.status == Resource.Status.ERROR) {
            emit(Resource.error(response.message!!))
        }
    }

}