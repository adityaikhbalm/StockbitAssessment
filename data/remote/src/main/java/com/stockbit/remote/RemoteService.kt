package com.stockbit.remote

import com.stockbit.model.CoinResponse
import retrofit2.http.GET

interface RemoteService {

    @GET("top/totaltoptiervolfull?limit=20&tsym=USD")
    suspend fun fetchTopTier(): CoinResponse

}