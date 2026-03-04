package com.konffach.app.network

import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.serialization.JsonConvertException
import kotlinx.serialization.SerializationException

/**
 * Maps Ktor / serialization exceptions to [NetworkError].
 * Deterministic; does not read response body to avoid suspend/engine issues.
 */
fun mapToNetworkError(throwable: Throwable): NetworkError {
    return when (throwable) {
        is ConnectTimeoutException, is SocketTimeoutException -> NetworkError.Timeout
        is ResponseException -> NetworkError.HttpError(
            statusCode = throwable.response.status.value,
            bodySnippet = null,
        )
        is ClientRequestException -> NetworkError.HttpError(
            statusCode = throwable.response.status.value,
            bodySnippet = null,
        )
        is JsonConvertException, is SerializationException -> NetworkError.SerializationError
        else -> NetworkError.UnknownError(throwable.message ?: throwable.toString())
    }
}
