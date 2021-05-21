package com.example.abriat.show.api

import com.example.abriat.show.liste.Show


class ShowApiListResponse (
    val score : Float,
    val show: Show
){

     fun extractListofShowFromResponse( listResponse :List<ShowApiListResponse>) : List<Show> {
         var showList = arrayListOf<Show>()
         for (response in listResponse) {
             showList.add(response.show)
         }
         return showList
     }
 }