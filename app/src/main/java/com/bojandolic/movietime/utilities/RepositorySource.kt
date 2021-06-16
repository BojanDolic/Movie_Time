package com.bojandolic.movietime.utilities

import retrofit2.Response
import java.lang.Exception

abstract class RepositorySource {

    protected suspend fun <T> getRemoteResult(call: suspend () -> Response<T>): Resource<T> {

        try {
            val response = call()
            if(response.isSuccessful) {
                val body = response.body()

                if(body != null) return Resource.success(body)

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