package com.valkotova.wishboxwithcompose.data.utils

import com.valkotova.wishboxwithcompose.data.model.responses.ErrorResponse
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import retrofit2.Response

abstract class BaseBackendApi (private val json: Json) {
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): T {
        return withContext(dispatcher) {
                try {
                    apiCall.invoke()
                } catch (throwable: Throwable) {
                    throw Throwable(parseError(throwable))
                }
            }
    }

    suspend fun safeApiCallUnit(
        apiCall: suspend () -> Response<Unit>
    ) {
        return withContext(dispatcher) {
            try {
                val r = apiCall.invoke()
                if (r.code() in 200..300) {
                } else {
                    val error = r.errorBody()?.string()
                        ?.let {
                            json.decodeFromString<ErrorResponse>(it)
                        }
                    throw Throwable(error?.message)
                }
            } catch (throwable: Throwable) {
                throw Throwable(parseError(throwable))
            }
        }
    }

    fun parseError(throwable: Throwable) = when(throwable){
        is HttpException -> {
            convertErrorBody(throwable)
        }
        else -> {
            throwable.message
        }
    }

    private fun convertErrorBody(throwable: HttpException): String? {
        return try {
            throwable.response()
                ?.errorBody()
                ?.string()
                ?.let { json.decodeFromString<ErrorResponse>(it) }
                ?.message
        }catch (e: Exception){
            e.message
        }
    }
}