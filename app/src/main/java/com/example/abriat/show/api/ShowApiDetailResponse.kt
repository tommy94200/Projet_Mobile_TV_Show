package com.example.abriat.show.api

import com.example.abriat.show.liste.Show
import com.example.abriat.show.liste.ShowImage
import com.example.abriat.show.liste.Show_Embedded
import com.example.abriat.show.liste.Show_Genre

data class ShowApiDetailResponse (
        val id : Int,
        val name : String,
        val image: ShowImage,
        val summary: String,
        val _embedded : Show_Embedded,
        val genres : List<String>?
)