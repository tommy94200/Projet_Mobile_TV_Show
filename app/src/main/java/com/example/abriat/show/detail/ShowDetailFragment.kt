package com.example.abriat.show.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.abriat.R
import com.example.abriat.show.api.ShowApi
import com.example.abriat.show.api.ShowApiDetailResponse
import com.example.abriat.show.api.ShowApiListResponse
import com.example.abriat.show.liste.Show
import com.example.abriat.show.liste.ShowAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ShowDetailFragment : Fragment() {

    //private var show:Show
    private lateinit var  textViewName : TextView
    private lateinit var  textViewResume : TextView
    private lateinit var  textViewGenre1: TextView
    private lateinit var  textViewGenre2 : TextView
    private lateinit var  textViewGenre3: TextView
    private lateinit var  textViewNBSaisons : TextView
    private lateinit var  imageView : ImageView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //sélection des vues
        textViewName = view.findViewById(R.id.show_nom_detail)
        textViewResume = view.findViewById(R.id.show_resume_detail)
        textViewGenre1 = view.findViewById(R.id.show_detail_genre1)
        textViewGenre1.visibility = View.INVISIBLE
        textViewGenre2 = view.findViewById(R.id.show_detail_genre2)
        textViewGenre2.visibility = View.INVISIBLE
        textViewGenre3 = view.findViewById(R.id.show_detail_genre3)
        textViewGenre3.visibility = View.INVISIBLE
        textViewNBSaisons = view.findViewById(R.id.show_detail_nb_saisons)

        imageView = view.findViewById(R.id.show_image_detail)

        var showID : Int? =arguments?.getInt("ShowID")
        //textViewName.text=  showID.toString() ?: "-1"

        //design pattern de retrofit
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.tvmaze.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val showApi: ShowApi = retrofit.create(ShowApi::class.java)

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_ShowDetailFragment_to_ShowListFragment)
        }

        showApi.getShowDetail(showID, "seasons").enqueue(object : Callback<ShowApiDetailResponse> { //requête HTTP vers le serveur en asynchrone

            override fun onResponse(call: Call<ShowApiDetailResponse>, response: Response<ShowApiDetailResponse>){ //réponse reçue sans erreurs
                if(response.isSuccessful && response.body() != null){
                    val element : ShowApiDetailResponse = response.body()!!
                    println("ici"+element.name)
                    textViewName.text = element.name
                    if(element.summary != null){
                        textViewResume.text = HtmlCompat.fromHtml(element.summary,0)
                    }

                    //affichage des images
                    if(element != null && element.image != null){
                        Glide
                                .with(imageView.context)
                                .load(element.image.medium)
                                //.centerCrop()
                                .into(imageView);
                    }
                    //récupération des genres
                   if(element.genres != null && !(element.genres.isEmpty()) ){
                       if(element.genres[0] != null){
                           textViewGenre1.text = element.genres[0]
                           textViewGenre1.visibility = View.VISIBLE
                       }
                       if(element.genres.count() >= 2 && element.genres[1] != null ){
                           textViewGenre2.text = element.genres[1]
                           textViewGenre2.visibility = View.VISIBLE
                       }
                       if(element.genres.count() >= 3 && element.genres[2] != null ){
                           textViewGenre3.text = element.genres[2]
                           textViewGenre3.visibility = View.VISIBLE
                       }
                   }

                    //récupération des saisons
                    val nb_saisons = element._embedded.seasons.count()
                    if(nb_saisons >1 ){
                        textViewNBSaisons.text = nb_saisons.toString()+" saisons"
                    }
                    else if(nb_saisons == 1 ){
                        textViewNBSaisons.text = "1 saison"
                    }
                    else{
                        textViewNBSaisons.visibility = View.INVISIBLE
                    }
                }
                else{
                    println("ici"+response.toString())
                }
            }
            override fun onFailure(call: Call<ShowApiDetailResponse>, t: Throwable) {
                println("ici"+call.toString()+" "+t.toString())
            }
        })
    }

}