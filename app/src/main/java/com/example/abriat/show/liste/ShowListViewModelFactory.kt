package com.example.abriat.show.liste

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ShowListViewModelFactory(application: Application, param: String) : ViewModelProvider.Factory {

    private var mApplication: Application? = null
    private var mParam: String? = null

    init {
        mApplication = application
        mParam = param
    }

    override  fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShowListViewModel(mApplication, mParam) as T
    }


}