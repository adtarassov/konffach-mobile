package com.konffach.app.network

/**
 * Cross-platform sealed error type for the network layer.
 */
sealed interface NetworkError {

    data object Timeout : NetworkError

    data class HttpError(
        val statusCode: Int,
        val bodySnippet: String? = null,
    ) : NetworkError

    data object SerializationError : NetworkError

    data class UnknownError(val message: String) : NetworkError
}
