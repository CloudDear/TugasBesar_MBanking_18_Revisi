package com.example.tugasbesar_mbanking_18

import retrofit2.Call
import retrofit2.http.*

interface api {
    @GET("memo/{cari}")
    fun getData(@Path("cari") cari:String? = null): Call<ResponseDataMemo>

    @FormUrlEncoded
    @POST("memo")
    fun createData(
        @Field("idmemo") id:String?,
        @Field("titlememo") title:String?,
        @Field("bodymemo") body:String?,
    ): Call<ResponseCreate>

    @DELETE("memo/{idmemo}")
    fun deleteData(@Path("idmemo")idmemo: String?): Call<ResponseCreate>

    @FormUrlEncoded
    @PUT("memo/{idmemo}")
    fun updateData(
        @Path("idmemo") idmemo:String?,
        @Field("titlememo") titlememo:String?,
        @Field("bodymemo") bodymemo:String?,
    ): Call<ResponseCreate>
}