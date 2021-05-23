package com.example.abriat.show.liste

import android.app.Application
import android.content.SharedPreferences
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.abriat.show.Singletons
import com.example.abriat.show.api.ShowApiListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ShowListViewModel(mApplication: Application?) : ViewModel(){
    val list : MutableLiveData<ShowListModel> = MutableLiveData()
    var request: MutableLiveData<String?> = MutableLiveData()
    val application = mApplication
    val pref: SharedPreferences = application!!.getSharedPreferences("Cache", 0) // 0 - for private mode
    val editor: SharedPreferences.Editor = pref.edit()

    init{
        loadRequestFromCache()
    }

    private fun loadRequestFromCache() {
        //récupération du cache
        var request : String? = pref.getString("lastSearch", "under"); // getting String
        this.request.value=request
    }

    fun getListfromApi(request : String?) {

        list.value=ShowListLoader

        Singletons.api.getShowList(request).enqueue(object : Callback<List<ShowApiListResponse>> { //requête HTTP vers le serveur en asynchrone

            override fun onResponse(call: Call<List<ShowApiListResponse>>, response: Response<List<ShowApiListResponse>>){ //réponse reçue sans erreurs
                if(response.isSuccessful && response.body() != null){
                    val listReponse :List<ShowApiListResponse> = response.body()!!
                    if(listReponse.isNotEmpty()) {
                        val listShow: List<Show> = listReponse.first().extractListofShowFromResponse(listReponse) //extraction des éléments à partir de la réponse
                        list.value = ShowListSuccess(listShow)
                    }else{
                        list.value= ShowListNotFound
                    }
                }
                else{
                    list.value = ShowListError
                }
            }
            override fun onFailure(call: Call<List<ShowApiListResponse>>, t: Throwable) {
            }
        })
    }


}