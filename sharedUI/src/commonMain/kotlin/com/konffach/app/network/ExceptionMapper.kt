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
    val errorMessage = throwable.message ?: throwable.toString()
    return when (throwable) {
        is ConnectTimeoutException, is SocketTimeoutException -> NetworkError.Timeout(message = errorMessage)
        is ResponseException -> NetworkError.HttpError(
            statusCode = throwable.response.status.value,
            bodySnippet = null,
            message = errorMessage
        )

        is ClientRequestException -> NetworkError.HttpError(
            statusCode = throwable.response.status.value,
            bodySnippet = null,
            message = errorMessage
        )

        is JsonConvertException, is SerializationException -> NetworkError.SerializationError(
            message = errorMessage
        )

        else -> NetworkError.UnknownError(message = errorMessage)
    }
}
