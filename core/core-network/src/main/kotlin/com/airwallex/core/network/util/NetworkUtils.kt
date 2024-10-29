package com.airwallex.core.network.util

import com.airwallex.core.basic.model.AirwallexException
import com.airwallex.core.network.error.ResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import java.util.Optional

fun String.toBearerToken() = "Bearer $this"

/***********************************************************
 *                  restful utils                          *
 ***********************************************************/

private val MEDIA_TYPE_JSON = "application/json; charset=UTF-8".toMediaType()

// canceled response
val Request.cancelled: okhttp3.Response
    get() = okhttp3.Response.Builder()
        .protocol(Protocol.HTTP_2)
        .code(600)
        .message("Request cancelled")
        .request(this)
        .body("".toResponseBody(MEDIA_TYPE_JSON))
        .build()

private fun OkHttpClient.Builder.cleanupInterceptor() {
    var foundLogger: Interceptor? = null

    with(interceptors()) {
        val newList = asSequence()
            .filter {
                if (it is HttpLoggingInterceptor) {
                    foundLogger = it
                    false
                } else {
                    true
                }
            }
            .toList()

        clear()
        addAll(newList)
    }
    if (foundLogger != null) {
        addNetworkInterceptor(foundLogger!!)
    }
}

fun OkHttpClient.Builder.build(reorderInterceptors: Boolean = true): OkHttpClient {
    if (reorderInterceptors) {
        cleanupInterceptor()
    }
    return build()
}

@Suppress("BlockingMethodInNonBlockingContext")
private suspend fun <T : Any> safeRestfulApiCall(apiCall: suspend () -> Response<T>): Result<Optional<T>> {
    try {
        val response = apiCall()

        return if (response.isSuccessful) {
            val body = response.body()
            body?.let {
                Result.success(Optional.of(body))
            } ?: Result.success(Optional.empty())
        } else {
            val code = response.code()
            val errorBody = response.errorBody()
            return Result.failure(
                ResponseException.matchFrom(code) ?: AirwallexException("No status code can be found. Error body: $errorBody")
            )
        }
    } catch (e: Exception) {
        return Result.failure(AirwallexException(e.message ?: e.toString(), e))
    }
}

suspend fun <T : Any> restfulApiFlow(apiCall: suspend () -> Response<T>): Flow<Result<Optional<T>>> =
    flow {
        emit(
            safeRestfulApiCall {
                apiCall()
            }
        )
    }.flowOn(Dispatchers.IO)

fun <T> Flow<Result<Optional<T>>>.requireBody(): Flow<Result<T>>
    = this.map { optionalResult ->
        optionalResult.map {
            if (it.isPresent) {
                it.get()
            } else {
                throw AirwallexException("No body found")
            }
        }
    }

fun interface ResultMapper<Source, Destination> {
    fun map(sourceResult: Result<Source>): Result<Destination>
}
