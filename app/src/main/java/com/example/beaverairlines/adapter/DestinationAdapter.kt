package com.example.beaverairlines.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.beaverairlines.R
import com.example.beaverairlines.data.model.Destination
import kotlinx.android.synthetic.main.destination_item.view.*

class DestinationAdapter(
    private val dataset: List<Destination>
): RecyclerView.Adapter<DestinationAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val city: TextView = view.findViewById(R.id.tv_location)
        val country: TextView = view.findViewById(R.id.tv_country)
        val image: ImageView = view.findViewById(R.id.iv_destination)
        val moreDetails: TextView = view.findViewById(R.id.tv_country)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.destination_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]

        holder.city.text = item.city
        holder.country.text = item.country
        holder.image.setImageResource(item.cityImage)


    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}