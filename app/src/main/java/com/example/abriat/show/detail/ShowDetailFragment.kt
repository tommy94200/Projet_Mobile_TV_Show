package com.example.abriat.show.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.abriat.R

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ShowDetailFragment : Fragment() {

    private lateinit var  textViewName : TextView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textViewName = view.findViewById(R.id.show_name)
        textViewName.text=  arguments?.getInt("ShowID").toString() ?: "-1"
        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_ShowDetailFragment_to_ShowListFragment)
        }
    }
}