package com.example.abriat.show.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.abriat.show.Singletons
import com.example.abriat.show.api.ShowApiDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowDetailViewModel(showID: Int?) : ViewModel(){
    var data : MutableLiveData<ShowDetailModel> = MutableLiveData()

    init{
        getShowDetailFromApi(showID)
    }

    private fun getShowDetailFromApi(showID : Int?) {
        data.value = ShowDetailLoader

        Singletons.api.getShowDetail(showID, "seasons").enqueue(object : Callback<ShowApiDetailResponse> { //requête HTTP vers le serveur en asynchrone

            override fun onResponse(call: Call<ShowApiDetailResponse>, response: Response<ShowApiDetailResponse>){ //réponse reçue sans erreurs
                if(response.isSuccessful && response.body() != null){
                    data.value=ShowDetailSuccess(response)
                    //displayItemDetails(response)
                }
                else{
                    data.value = ShowDetailNotFound
                }
            }


            override fun onFailure(call: Call<ShowApiDetailResponse>, t: Throwable) {
                data.value = ShowDetailError
            }
        })
    }
}