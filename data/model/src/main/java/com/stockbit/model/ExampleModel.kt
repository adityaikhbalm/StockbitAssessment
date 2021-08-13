package com.stockbit.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ExampleModel(

    @PrimaryKey
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String

)