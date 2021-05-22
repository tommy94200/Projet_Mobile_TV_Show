package com.example.abriat.show.liste


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
//import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.abriat.R



class ShowAdapter(private var dataSet: List<Show>, var listener:((Show)->Unit)?) :   RecyclerView.Adapter<ShowAdapter.ViewHolder>() {


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val imageView : ImageView

        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.show_nom)
            imageView = view.findViewById(R.id.show_image)

        }
    }

    fun updateList(list: List<Show>){
        dataSet = list
        notifyDataSetChanged()
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.show_item, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val show = dataSet[position]
        viewHolder.textView.text = show.name

        //affichage des images
        if(show.image != null){
            Glide
                .with(viewHolder.imageView.context)
                .load(show.image.medium)
                //.centerCrop()
                .into(viewHolder.imageView);
        }

        //listener
        viewHolder.itemView.setOnClickListener{
            listener?.invoke(show)
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size



}

