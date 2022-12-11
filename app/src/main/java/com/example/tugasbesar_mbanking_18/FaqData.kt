package com.example.tugasbesar_mbanking_18

import com.google.gson.annotations.SerializedName

data class FaqData (
    @SerializedName("idfaq") val id:String,
    @SerializedName("titlefaq") val title:String,
    @SerializedName("bodyfaq") val body:String,
)