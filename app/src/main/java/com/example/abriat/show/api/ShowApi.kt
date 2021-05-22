package com.example.abriat.show.api

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Query


interface ShowApi {
    @GET("search/shows")
    fun getShowList(@Query("q") show_name: String?): Call<List<ShowApiListResponse>>

    @GET("/shows/{id}")
    fun getShowDetail(@Path("id") show_id: Int?, @Query("embed") show_query: String?): Call<ShowApiDetailResponse>

}