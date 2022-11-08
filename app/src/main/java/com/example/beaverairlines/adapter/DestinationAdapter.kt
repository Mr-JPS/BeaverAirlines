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
        val city: TextView = view.findViewById(R.id.location_tv)
        val image: ImageView = view.findViewById(R.id.destination_image)
        val moreDetails: TextView = view.findViewById(R.id.moreDetails_tv)
        val destinationCard: CardView = view.findViewById(R.id.destinationCard_cv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.destination_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]

        holder.city.text = item.city
        holder.image.setImageResource(item.cityImage)

        holder.moreDetails.setOnClickListener{
            if (holder.destinationCard.visibility == View.INVISIBLE){
                holder.destinationCard.visibility = View.VISIBLE
                holder.destinationCard.visibility = View.VISIBLE
            } else {
                holder.destinationCard.visibility = View.INVISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}