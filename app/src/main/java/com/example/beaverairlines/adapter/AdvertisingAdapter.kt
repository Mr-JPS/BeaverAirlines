package com.example.beaverairlines.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beaverairlines.R
import com.example.beaverairlines.data.model.Advertising

class AdvertisingAdapter (
    private val dataset: List<Advertising>
        ): RecyclerView.Adapter<AdvertisingAdapter.ItemViewHolder>()  {

    class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.tv_title)
        val banner: ImageView = view.findViewById(R.id.iv_banner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.advertising_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
       val ad = dataset[position]

        holder.title.text = ad.title
        holder.banner.setImageResource(ad.image)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}