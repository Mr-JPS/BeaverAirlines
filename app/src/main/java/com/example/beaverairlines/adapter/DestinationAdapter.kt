package com.example.beaverairlines.adapter

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.beaverairlines.R
import com.example.beaverairlines.data.model.Destination

class DestinationAdapter(
    private val dataset: List<Destination>
): RecyclerView.Adapter<DestinationAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val city1: TextView = view.findViewById(R.id.dest_tv_field1)
        val country1: TextView = view.findViewById(R.id.dest_tv_country)
        val image: ImageView = view.findViewById(R.id.dest_iv_destination)
        val bttnOpen: ImageView = view.findViewById(R.id.dest_bttn_open)
        val descripBig: TextView = view.findViewById(R.id.dest_tv_descrip_big)
        val descripCityBig: TextView = view.findViewById(R.id.dest_tv_field_big)
        val descripCountryBig: TextView = view.findViewById(R.id.dest_tv_country_big)
        val bttnClose: TextView = view.findViewById(R.id.dest_bttn_close)
        val mainCard: CardView = view.findViewById(R.id.dest_mainCard)
        val cloudPic: ImageView = view.findViewById(R.id.dest_iv_cloud)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.destination_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        val cloud = R.drawable.cloudcrop

        holder.city1.text = item.city
        holder.country1.text = item.country
        holder.image.setImageResource(item.cityImage)
        holder.descripCityBig.text = item.city
        holder.descripCountryBig.text = item.country
        holder.descripBig.text = item.cityInfo
        holder.bttnClose.text = "CLOSE"
        holder.cloudPic.setImageResource(cloud)


        holder.mainCard.setOnClickListener {
            if (holder.descripBig.visibility == View.GONE){
                TransitionManager.beginDelayedTransition(holder.mainCard, AutoTransition())
                holder.descripBig.visibility = View.VISIBLE
                holder.descripCityBig.visibility = View.VISIBLE
                holder.descripCountryBig.visibility = View.VISIBLE
                holder.bttnClose.visibility = View.VISIBLE
                holder.cloudPic.visibility = View.VISIBLE

                holder.city1.visibility = View.GONE
                holder.country1.visibility = View.GONE
                holder.bttnOpen.visibility = View.GONE

            } else {
                holder.descripBig.visibility = View.GONE
                holder.descripCityBig.visibility = View.GONE
                holder.descripCountryBig.visibility = View.GONE
                holder.bttnClose.visibility = View.GONE
                holder.cloudPic.visibility = View.GONE

                TransitionManager.beginDelayedTransition(holder.mainCard, AutoTransition())
                holder.city1.visibility = View.VISIBLE
                holder.country1.visibility = View.VISIBLE
                holder.bttnOpen.visibility = View.VISIBLE

            }
        }


        holder.bttnOpen.setOnClickListener {
                TransitionManager.beginDelayedTransition(holder.mainCard, AutoTransition())
                holder.descripBig.visibility = View.VISIBLE
                holder.descripCityBig.visibility = View.VISIBLE
                holder.descripCountryBig.visibility = View.VISIBLE
                holder.bttnClose.visibility = View.VISIBLE
                holder.cloudPic.visibility = View.VISIBLE

                holder.city1.visibility = View.GONE
                holder.country1.visibility = View.GONE
                holder.bttnOpen.visibility = View.GONE

            }

        holder.bttnClose.setOnClickListener {
            holder.descripBig.visibility = View.GONE
            holder.descripCityBig.visibility = View.GONE
            holder.descripCountryBig.visibility = View.GONE
            holder.bttnClose.visibility = View.GONE
            holder.cloudPic.visibility = View.GONE

            TransitionManager.beginDelayedTransition(holder.mainCard, AutoTransition())
            holder.city1.visibility = View.VISIBLE
            holder.country1.visibility = View.VISIBLE
            holder.bttnOpen.visibility = View.VISIBLE

        }



    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}