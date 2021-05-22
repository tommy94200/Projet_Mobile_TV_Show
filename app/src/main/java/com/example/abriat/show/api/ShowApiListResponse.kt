package com.example.abriat.show.api

import com.example.abriat.show.liste.Show_ListItem


class ShowApiListResponse (
    val score : Float,
    val show: Show_ListItem
){

     fun extractListofShowFromResponse( listResponse :List<ShowApiListResponse>) : List<Show_ListItem> {
         var showList = arrayListOf<Show_ListItem>()
         for (response in listResponse) {
             showList.add(response.show)
         }
         return showList
     }
 }