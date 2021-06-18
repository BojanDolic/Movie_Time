package com.bojandolic.movietime.utilities

import android.util.Log
import retrofit2.Response
import java.lang.Exception

abstract class RepositorySource {

    protected suspend fun <T> getRemoteResult(call: suspend () -> Response<T>): Resource<T> {

        try {
            val response = call()
            if(response.isSuccessful) {
                val body = response.body()
                //Log.d("TAG", "getRemoteResult: ${response.raw().request().url().uri().toASCIIString()}")
                if(body != null) return Resource.success(body) else return Resource.error("Body of the network request is null")

            }
            return Resource.error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.error("Network call failed for the following reason: $message")
    }

}