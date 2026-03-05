package com.konffach.app.network

/**
 * Cross-platform sealed error type for the network layer.
 */
sealed interface NetworkError {

    val message: String

    data class Timeout(override val message: String) : NetworkError

    data class HttpError(
        val statusCode: Int,
        val bodySnippet: String? = null,
        override val message: String,
    ) : NetworkError

    data class SerializationError(override val message: String) : NetworkError

    data class UnknownError(override val message: String) : NetworkError
}
