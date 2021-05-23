package com.example.abriat.show.liste

sealed class ShowListModel

data class ShowListSuccess (val showList: List<Show>): ShowListModel()
object ShowListError : ShowListModel()
object ShowListLoader : ShowListModel()
object ShowListNotFound : ShowListModel()