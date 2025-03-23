package com.basket.core.common.result

/**
 * Wrap a suspending [call] in try/catch. In case an exception is thrown, a [Result.Failure] is
 * created based on the [Throwable].
 */
suspend fun <T : Any, E> callSafe(
    call: suspend () -> Result<T, E>,
    onError: (Throwable) -> Result<Nothing, E>,
): Result<T, E> {
    return try {
        call()
    } catch (e: Throwable) {
        onError(e)
    }
}
/*
*
 * @since 0.1.0
 * @see callSafe
suspend fun <T : Any, E> callSafe(
    call: suspend () -> Result<T, E>,
    lazyMessage: () -> Any
) = callSafe(call, errorCode, lazyMessage().toString())*/

/**
 *  Method for sequential call to local persistence and server request
 *
 *  @param[localCall] Call to local persistence (cache, db, prefs etc.)
 *  @param[remoteCall] Call to server
 *
 *  @return [ReceiveChannel] emitting loaded data with [Result]
 *  @since 0.1.0
 */
/*suspend fun <T : Any> combinedCall(
    localCall: suspend () -> T?,
    remoteCall: suspend () -> Result<T>
): ReceiveChannel<Result<T>> = CoroutineScope(coroutineContext).produce {
    // Send info about running process with empty data
    send(Result.Running<T>())
    // TODO improve content of this method to run both task concurrently - needs to manage correct sequence of states!!
    // Send info about running process with local cached data if available
    val cachedLocalData = localCall()
    send(Result.Running(cachedLocalData))

    // Send result of remote request
    val remoteResult = when (val remoteCallResult = remoteCall()) {
        is Result.Error -> remoteCallResult.copy(data = cachedLocalData)
        else -> remoteCallResult
    }

    send(remoteResult)
}*/
