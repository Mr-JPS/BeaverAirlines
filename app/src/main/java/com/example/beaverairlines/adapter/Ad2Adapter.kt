package com.example.beaverairlines.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beaverairlines.R
import com.example.beaverairlines.data.Iata
import com.example.beaverairlines.data.model.Advertising2

class Ad2Adapter (
    private var dataset: List<Advertising2>
): RecyclerView.Adapter<Ad2Adapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        //val iataCode: TextView = view.findViewById(R.id.item_iataCode)
        val ad2Pic: ImageView = view.findViewById(R.id.BookFragment_adPic)
    }

    fun submitList(list : List<Advertising2>){
        dataset = list
        notifyDataSetChanged()

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.ad2_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val ad2 = dataset[position]

        //holder.iataCode.text = iata.iata
        holder.ad2Pic.setImageResource(ad2.ad2Pic)

    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}