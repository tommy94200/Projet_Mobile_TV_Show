package com.example.abriat.disposition.api

import android.R
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import com.example.abriat.disposition.liste.Show
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class ShowApiResponse (
    val score : Float,
    val show: Show
){

     fun extractListofShowFromResponse( listResponse :List<ShowApiResponse>) : List<Show> {
         var showList = arrayListOf<Show>()
         for (response in listResponse) {
             showList.add(response.show)
         }
         return showList
     }
 }