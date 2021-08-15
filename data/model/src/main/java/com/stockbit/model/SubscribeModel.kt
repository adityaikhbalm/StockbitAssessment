package com.stockbit.model

import com.google.gson.annotations.SerializedName

data class SubscribeModel (
    @SerializedName("action")
    val action: String,
    @SerializedName("subs")
    val subs: List<String>
)