package com.example.kotlinrecyclerviewexercise.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.kotlinrecyclerviewexercise.model.FactsFeed
import com.example.kotlinrecyclerviewexercise.R
import kotlinx.android.synthetic.main.dynamic_cell_item.view.*


class DynamicListAdapter(private val factsFeed: FactsFeed):RecyclerView.Adapter<CustomViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.dynamic_cell_item,parent,false)
        return CustomViewHolder(
            cellForRow
        )
    }

    override fun getItemCount(): Int {
        return factsFeed.rows.count()
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val row= factsFeed.rows[position]
        holder.view.title_textview_dynamic_cell?.text=row.title
        holder.view.description_textview_dynamic_cell.text=row.description

        //loading each row image using glide library
        val targetImageView= holder.view.imageView
        Glide.with(holder.view.context) //1
            .load(row.imageHref)
           .placeholder(R.drawable.placeholder_fallback)// added fallback image placeholder if the loading image failed
            .error(R.drawable.error_image)/// added error image placeholder if the loading image failed
            .skipMemoryCache(false) // loads images lazily
            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
            .into(targetImageView)

    }

}
class CustomViewHolder(val view: View):RecyclerView.ViewHolder(view){

}

