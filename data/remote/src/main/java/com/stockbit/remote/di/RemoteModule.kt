package com.stockbit.remote.di

import com.google.gson.Gson
import com.stockbit.remote.BuildConfig
import com.stockbit.remote.RemoteDatasource
import com.stockbit.remote.RemoteService
import com.stockbit.remote.SocketService
import com.stockbit.remote.utils.Constants.BACKOFF_DURATION_BASE
import com.stockbit.remote.utils.Constants.BACKOFF_DURATION_MAX
import com.stockbit.remote.utils.Constants.OKHTTP_CONNECT_TIMEOUT
import com.stockbit.remote.utils.Constants.PARAM_API_KEY
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.retry.ExponentialWithJitterBackoffStrategy
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.tinder.streamadapter.coroutines.CoroutinesStreamAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val remoteModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), get()) }
    single { provideRemoteService(get()) }
    single { provideSocketService(get()) }

    factory { RemoteDatasource(get(), get()) }

    single { provideGson() }

    single {
        provideScarlet(
            okHttpClient = get(),
            gson = get(),
            backoffStrategy = get()
        )
    }

    single { provideBackOffStrategy() }
}

private fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient
        .Builder()
        .connectTimeout(OKHTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(OKHTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(OKHTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    level = HttpLoggingInterceptor.Level.HEADERS
                })
        .addInterceptor { chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter(PARAM_API_KEY, BuildConfig.TOKEN)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            chain.proceed(request)
        }
        .build()
}

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    gson: Gson,
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()
}

private fun provideRemoteService(retrofit: Retrofit): RemoteService =
    retrofit.create(RemoteService::class.java)

private fun provideScarlet(
    okHttpClient: OkHttpClient,
    gson: Gson,
    backoffStrategy: ExponentialWithJitterBackoffStrategy
): Scarlet {
    return Scarlet.Builder()
        .webSocketFactory(
            okHttpClient
                .newWebSocketFactory(BuildConfig.WS_BASE_URL)
        )
        .addMessageAdapterFactory(GsonMessageAdapter.Factory(gson))
        .addStreamAdapterFactory(CoroutinesStreamAdapterFactory())
        .backoffStrategy(backoffStrategy)
        .build()
}

private fun provideSocketService(scarlet: Scarlet): SocketService =
    scarlet.create(SocketService::class.java)

fun provideBackOffStrategy(): ExponentialWithJitterBackoffStrategy {
    return ExponentialWithJitterBackoffStrategy(
        BACKOFF_DURATION_BASE,
        BACKOFF_DURATION_MAX
    )
}

fun provideGson(): Gson {
    return Gson().newBuilder()
        .serializeNulls()
        .create()
}