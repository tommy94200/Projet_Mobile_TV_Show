package com.example.abriat.disposition.api

import com.example.abriat.disposition.liste.Show
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Query


interface ShowApi {
    @GET("shows")
    fun getShowList(@Query("q") show_name: String?): Call<List<ShowApiResponse>>

}