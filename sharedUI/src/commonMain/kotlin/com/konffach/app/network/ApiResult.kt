package com.konffach.app.network

/**
 * Result wrapper for API calls. Use with [safeApiCall].
 */
sealed interface ApiResult<out T> {

    data class Success<T>(val data: T) : ApiResult<T>

    data class Error(val error: NetworkError) : ApiResult<Nothing>
}

inline fun <T> ApiResult<T>.onSuccess(block: (T) -> Unit): ApiResult<T> {
    if (this is ApiResult.Success) block(data)
    return this
}

inline fun <T> ApiResult<T>.onError(block: (NetworkError) -> Unit): ApiResult<T> {
    if (this is ApiResult.Error) block(error)
    return this
}

/**
 * Runs a suspend network call and wraps the result in [ApiResult].
 * Catches exceptions and maps them to [NetworkError] via [mapToNetworkError].
 *
 * Usage in repositories:
 * ```
 * suspend fun getPosts(): ApiResult<List<PostDto>> = safeApiCall {
 *   httpClient.get("https://api.example.com/posts").body()
 * }
 * ```
 */
suspend inline fun <T> safeApiCall(crossinline block: suspend () -> T): ApiResult<T> {
    val result = runCatching { block() }
    return result.fold(
        onSuccess = { data: T -> ApiResult.Success(data) },
        onFailure = { e: Throwable -> ApiResult.Error(mapToNetworkError(e)) },
    )
}
