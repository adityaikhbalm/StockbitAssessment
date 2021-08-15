package com.stockbit.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class CoinModel(
    @PrimaryKey
    @SerializedName("Id")
    var id: String = "0",
    @SerializedName("Name")
    var name: String? = null,
    @SerializedName("FullName")
    var fullName: String? = null,
    @Ignore
    var price: Double = 0.0,
    @Ignore
    var volume: Double = 0.0
)