package com.stockbit.model

import com.google.gson.annotations.SerializedName

data class CoinResponse(
    @SerializedName("Data")
    val data: List<CoinInfo>
)

data class CoinInfo(
    @SerializedName("CoinInfo")
    val coinInfo: CoinModel
)