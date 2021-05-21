package com.example.abriat.show.detail

import android.content.SharedPreferences
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
    private lateinit var  textViewBox1: TextView
    private lateinit var  textViewBox2 : TextView
    private lateinit var  textViewBox3: TextView
    private lateinit var  textViewBox4 : TextView
    private lateinit var  imageView : ImageView
    private lateinit var searchRequest : String

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        //récupération du cache
        val pref: SharedPreferences = requireContext().getSharedPreferences("Cache", 0) // 0 - for private mode
        val editor: SharedPreferences.Editor = pref.edit()

        var request : String? = pref.getString("lastSearch", "under"); // getting String
        println("ici"+request)

        //sélection des vues
        textViewName = view.findViewById(R.id.show_nom_detail)
        textViewResume = view.findViewById(R.id.show_resume_detail)
        textViewBox1 = view.findViewById(R.id.show_detail_box1)
        textViewBox1.visibility = View.INVISIBLE
        textViewBox2 = view.findViewById(R.id.show_detail_box2)
        textViewBox2.visibility = View.INVISIBLE
        textViewBox3 = view.findViewById(R.id.show_detail_box3)
        textViewBox3.visibility = View.INVISIBLE
        textViewBox4 = view.findViewById(R.id.show_detail_box4)
        textViewBox4.visibility = View.INVISIBLE

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
                    displayItemDetails(response)
                }
                else{
                    //println("ici"+response.toString())
                }
            }


            override fun onFailure(call: Call<ShowApiDetailResponse>, t: Throwable) {
                //println("ici"+call.toString()+" "+t.toString())
            }
        })
    }

    fun setInfoBox(textview : TextView,  informations : ArrayList<String>){
        if(informations != null && !(informations.isEmpty()) && informations.first() != null  ) {

                textview.text = informations.first().toString()
                textview.visibility = View.VISIBLE
                informations.removeAt(0)
        }
    }

    private fun displayItemDetails(response: Response<ShowApiDetailResponse>) {
        val element: ShowApiDetailResponse = response.body()!!
        println("ici" + element.name)
        textViewName.text = element.name
        if (element.summary != null) {
            textViewResume.text = HtmlCompat.fromHtml(element.summary, 0)
        } else {
            textViewResume.text = "Description not yet available."
        }

        //affichage de l'image
        if (element != null && element.image != null) {
            Glide
                    .with(imageView.context)
                    .load(element.image.medium)
                    //.centerCrop()
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.unknown)
        }


        //récupération du nombre de saisons
        val nb_saisons = element._embedded.seasons.count()
        if (nb_saisons >= 1) {
            if (element.genres.size >= 3) {
                element.genres.add(3, nb_saisons.toString() + " season(s)")
            } else {
                element.genres.add(nb_saisons.toString() + " season(s)")
            }

        }
        //récupération des genres
        setInfoBox(textViewBox1, element.genres)
        setInfoBox(textViewBox2, element.genres)
        setInfoBox(textViewBox3, element.genres)
        setInfoBox(textViewBox4, element.genres)
    }
}