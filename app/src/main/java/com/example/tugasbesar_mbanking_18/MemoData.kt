package com.example.tugasbesar_mbanking_18

import com.google.gson.annotations.SerializedName

data class MemoData (
    @SerializedName("idmemo") val id:String,
    @SerializedName("titlememo") val title:String,
    @SerializedName("bodymemo") val body:String,
)