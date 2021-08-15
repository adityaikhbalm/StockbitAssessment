package com.stockbit.remote.utils

object Constants {
    const val PARAM_API_KEY = "api_key"

    // Retry connection
    const val BACKOFF_DURATION_BASE = 1000L
    const val BACKOFF_DURATION_MAX = 1000L
    const val OKHTTP_CONNECT_TIMEOUT = 20L
    const val ONE_SECOND_DELAY = 1000L
}