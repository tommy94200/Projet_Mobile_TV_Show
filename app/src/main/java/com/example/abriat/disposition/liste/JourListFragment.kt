package com.example.abriat.disposition.liste

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.abriat.R

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class JourListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView //recyclerview
    private val adapteur= JourAdapter(listOf())
    private val layoutManager = LinearLayoutManager(context)


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jour_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.jour_recyclerview)

        recyclerView.apply{
            layoutManager = this@JourListFragment.layoutManager
            adapter = this@JourListFragment.adapteur

        }

        val jourList = arrayListOf<Jour>().apply{
            add(Jour(10, "Lundi"))
            add(Jour(11, "Mardi"))
            add(Jour(12, "Mercredi"))
        }

        adapteur.updateList(jourList)

    }
}