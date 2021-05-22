package com.example.abriat.show.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ShowDetailViewModelFactory(showId : Int?) : ViewModelProvider.Factory {

    private var mshowId: Int?

    init {
        mshowId = showId
    }

    override  fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShowDetailViewModel(mshowId) as T
    }

}