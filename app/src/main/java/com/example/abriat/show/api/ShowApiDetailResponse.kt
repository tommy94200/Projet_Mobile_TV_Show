package com.example.abriat.show.api

import com.example.abriat.show.liste.ShowImage
import com.example.abriat.show.detail.Show_Embedded

data class ShowApiDetailResponse (
        val id : Int,
        val name : String,
        val image: ShowImage,
        val summary: String,
        val _embedded : Show_Embedded,
        var genres : ArrayList<String>
)