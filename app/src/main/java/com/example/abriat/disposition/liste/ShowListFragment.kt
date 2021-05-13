package com.example.abriat.disposition.liste

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.abriat.R
import com.example.abriat.disposition.api.ShowApi
import com.example.abriat.disposition.api.ShowApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ShowListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView //recyclerview
    private val adapteur= ShowAdapter(listOf())
    private val layoutManager = LinearLayoutManager(context)


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.show_recyclerview)

        recyclerView.apply{
            layoutManager = this@ShowListFragment.layoutManager
            adapter = this@ShowListFragment.adapteur

        }

        //design pattern de retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.tvmaze.com/search/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val showApi: ShowApi = retrofit.create(ShowApi::class.java)


        showApi.getShowList("lucifer").enqueue(object : Callback<List<ShowApiResponse>>{ //requête HTTP vers le serveur en asynchrone

            override fun onResponse(call: Call<List<ShowApiResponse>>,response: Response<List<ShowApiResponse>>){ //réponse reçue sans erreurs
                if(response.isSuccessful && response.body() != null){
                    val listReponse :List<ShowApiResponse> = response.body()!!
                    val listShow : List<Show> = listReponse.first().extractListofShowFromResponse(listReponse) //extraction des éléments à partir de la réponse
                      this@ShowListFragment.adapteur.updateList(listShow) //rafraîchissement de l'adapteur
                    println("ici reponse successful : "+ listReponse.first().toString())
                }
                else{
                    println("ici"+response.toString())
                }
            }
            override fun onFailure(call: Call<List<ShowApiResponse>>, t: Throwable) {
                println("ici"+call.toString()+" "+t.toString())
                TODO("Not yet implemented")
            }
        })

    }
}