package com.example.beaverairlines.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beaverairlines.R
import com.example.beaverairlines.data.model.Advertising
import com.example.beaverairlines.data.model.CabinClass

class CabinClassAdapter (
    private val dataset: List<CabinClass>
): RecyclerView.Adapter<CabinClassAdapter.ItemViewHolder>()  {


    class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val header : TextView = view.findViewById(R.id.tv_cabinHeader)
        val image: ImageView = view.findViewById(R.id.iv_cabin)
        val title: TextView = view.findViewById(R.id.tv_cabinTitle)
        val description: TextView = view.findViewById(R.id.tv_cabinDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.cabin_class_card, parent, false)

        return CabinClassAdapter.ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val cabin = dataset[position]

        holder.header.text = cabin.title
        holder.image.setImageResource(cabin.image)
        holder.title.text = cabin.title
        holder.description.text = cabin.description
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}