package com.example.abriat.show.api

import com.example.abriat.show.liste.Show


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