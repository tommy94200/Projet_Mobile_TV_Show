package com.example.abriat.show.liste

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.abriat.R
import com.example.abriat.show.api.ShowApi
import com.example.abriat.show.api.ShowApiListResponse
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ShowListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView //recyclerview
    private val adapteur= ShowAdapter(listOf(), ::onClickedShow)


    //private val layoutManager: AutoGridLayoutManager? = AutoGridLayoutManager(context, 500)


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
            layoutManager = AutoGridLayoutManager(context, 500)
            adapter = this@ShowListFragment.adapteur

        }

        //design pattern de retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.tvmaze.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val showApi: ShowApi = retrofit.create(ShowApi::class.java)

        callSearchApi(showApi, "under")

        //partie recherche
        val search = view.findViewById(R.id.searchbar) as EditText
        search.visibility = View.INVISIBLE
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view2:View ->
            when(search.visibility) {
                View.INVISIBLE -> {
                    search.visibility = View.VISIBLE
                }
                View.VISIBLE -> {
                    search.visibility = View.INVISIBLE
                    val request = search.text.toString()   //display the text that you entered in edit text
                    if(request != null){
                        callSearchApi(showApi,request)
                    }
                }
            }
        }

    }

    private fun onClickedShow(show: Show){
        findNavController().navigate(R.id.action_ShowListFragment_to_ShowDetailFragment, bundleOf(
                "ShowID" to show.id
        ))
    }

    private fun callSearchApi(showApi :ShowApi, query: String){
        showApi.getShowList(query).enqueue(object : Callback<List<ShowApiListResponse>>{ //requête HTTP vers le serveur en asynchrone

            override fun onResponse(call: Call<List<ShowApiListResponse>>, response: Response<List<ShowApiListResponse>>){ //réponse reçue sans erreurs
                if(response.isSuccessful && response.body() != null){
                    val listReponse :List<ShowApiListResponse> = response.body()!!
                    if(listReponse.isNotEmpty()) {
                        val listShow: List<Show> = listReponse.first().extractListofShowFromResponse(listReponse) //extraction des éléments à partir de la réponse
                        this@ShowListFragment.adapteur.updateList(listShow) //rafraîchissement de l'adapteur
                    }else{
                        Toast.makeText(context, "No results founds for your request", Toast.LENGTH_LONG).show() // display the toast on home button click
                    }
                 }
                else{
                    println("ici"+response.toString())
                }
            }
            override fun onFailure(call: Call<List<ShowApiListResponse>>, t: Throwable) {
                println("ici"+call.toString()+" "+t.toString())
            }
        })
    }
}