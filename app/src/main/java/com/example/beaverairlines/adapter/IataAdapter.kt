package com.example.beaverairlines.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beaverairlines.R
import com.example.beaverairlines.data.Iata

class IataAdapter (
    private var dataset: List<Iata>
): RecyclerView.Adapter<IataAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        //val iataCode: TextView = view.findViewById(R.id.item_iataCode)
        val iataAirportName: TextView = view.findViewById(R.id.item_iataAirportName)
    }

    fun submitList(list : List<Iata>){
        dataset = list
        notifyDataSetChanged()

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.iata_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val iata = dataset[position]

        //holder.iataCode.text = iata.iata
        holder.iataAirportName.text = iata.name

    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}