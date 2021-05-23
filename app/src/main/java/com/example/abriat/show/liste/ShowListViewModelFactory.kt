package com.example.abriat.show.liste

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ShowListViewModelFactory(application: Application) : ViewModelProvider.Factory {

    private var mApplication: Application? = null

    init {
        mApplication = application
    }

    override  fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShowListViewModel(mApplication) as T
    }


}