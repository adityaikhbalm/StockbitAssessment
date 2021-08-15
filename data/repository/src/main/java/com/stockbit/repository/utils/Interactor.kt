package com.stockbit.repository.utils

import com.stockbit.remote.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

suspend fun <T : Any> fetch(call: suspend () -> T): Flow<Resource<T>> = flow {
    emit(Resource.loading<T>(null))
    try {
        emit(Resource.success(data = call.invoke()))
    } catch (e: Throwable) {
        emit(Resource.error<T>(error = e, data = null))
    }
}

suspend fun <T> retryConnection(
    times: Int = Int.MAX_VALUE,
    initialDelay: Long = 100,
    maxDelay: Long = Constants.ONE_SECOND_DELAY,
    factor: Double = 2.0,
    block: suspend () -> T
): T {
    var currentDelay = initialDelay
    repeat(times - 1) {
        try {
            return block()
        } catch (e: IOException) { }
        delay(currentDelay)
        currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
    }
    return block()
}