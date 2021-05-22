package com.example.abriat.show.liste

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.abriat.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ShowListViewFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView //recyclerview
    private val adapteur= ShowListAdapter(listOf(), ::onClickedShow)
    private lateinit var loader: ProgressBar



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
        loader=view.findViewById(R.id.loader)
        recyclerView.apply{
            layoutManager = AutoGridLayoutManager(context, 500)
            adapter = this@ShowListViewFragment.adapteur

        }

        val viewModelFactory = ShowListViewModelFactory(requireActivity().application, "under")
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ShowListViewModel::class.java)


        //récupération du cache
        val pref: SharedPreferences = requireContext().getSharedPreferences("Cache", 0) // 0 - for private mode
        val editor: SharedPreferences.Editor = pref.edit()


        var request : String? = pref.getString("lastSearch", "under"); // getting String
        println("ici"+request)

        viewModel.request.value=request

        viewModel.request.observe(viewLifecycleOwner, Observer{list ->
            viewModel.getListfromApi(viewModel.request.value)
            println("ici + showModel.request observer ="+viewModel.request.value)
        })

        viewModel.list.observe(viewLifecycleOwner, Observer{showListModel ->
            loader.isVisible = showListModel is ShowListLoader
            when(showListModel){
                is ShowListSuccess -> adapteur.updateList(showListModel.showList)
                ShowListError -> Toast.makeText(context, "Oups... Something did wrong with your request", Toast.LENGTH_LONG).show()
                ShowListNotFound -> Toast.makeText(context, "No results founds for your request", Toast.LENGTH_LONG).show()
                else -> Unit
            }
        })


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
                     request = search.text.toString()   //display the text that you entered in edit text
                    if(request != null){
                        println("ici"+request)
                        search.setEnabled(false);
                        search.setEnabled(true);
                        editor.putString("lastSearch", request); // Storing string
                        editor.commit(); // commit changes on cache
                        viewModel.request.value=request
                        println("ici + showModel.request ="+viewModel.request.value)
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


}