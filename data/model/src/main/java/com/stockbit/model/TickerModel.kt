package com.stockbit.model

import com.google.gson.annotations.SerializedName

data class TickerModel (
    @SerializedName("TYPE")
    val type: Int,
    @SerializedName("FROMSYMBOL")
    val symbol: String,
    @SerializedName("PRICE")
    val price: Double,
    @SerializedName("OPENDAY")
    val openDay: Double,
    @SerializedName("VOLUME24HOURTO")
    val volume: Double
)