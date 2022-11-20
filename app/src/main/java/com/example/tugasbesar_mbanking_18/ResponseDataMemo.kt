package com.example.tugasbesar_mbanking_18

import com.google.gson.annotations.SerializedName

data class ResponseDataMemo (
    @SerializedName("status") val stt:String,
    val totaldata: Int,
    val data:List<MemoData>)