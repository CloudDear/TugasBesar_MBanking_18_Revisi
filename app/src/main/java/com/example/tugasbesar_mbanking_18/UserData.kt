package com.example.tugasbesar_mbanking_18

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("userId") val idUser:Int,
    @SerializedName("username") val nameUser:String,
    @SerializedName("password") val passwordUser: String,
    @SerializedName("email") val emailUser:String,
    @SerializedName("tglLahir") val tglLahir:String,
    @SerializedName("noTelp") val noTelp:String,
    )
