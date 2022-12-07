package com.example.beaverairlines.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.beaverairlines.R
import com.example.beaverairlines.data.model.Advertising
import com.example.beaverairlines.data.model.CabinClass
import kotlinx.android.synthetic.main.book3_card.view.*

// THIS ADAPTER MANAGES THE FUNCTIONALITIES FOR SHOWING THE AVL CABIN CLASSES WHILE BOOKING A FLIGHT IN THE BOOK FRAGMENT

class CabinClassAdapter (
    private val dataset: List<CabinClass>
): RecyclerView.Adapter<CabinClassAdapter.ItemViewHolder>()  {

    //PROVIDING A CABIN CLASS UNIT FOR PASSING ITS VALUE:
    private var onItemClickListener: ((CabinClass)->Unit)? = null
    internal fun setOnItemClickListener(listener: (CabinClass)->Unit) {
        onItemClickListener = listener
    }



    class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val header : TextView = view.findViewById(R.id.tv_cabinHeader)
        val image: ImageView = view.findViewById(R.id.iv_cabin)
        var title: TextView = view.findViewById(R.id.tv_cabinTitle)
        val description: TextView = view.findViewById(R.id.tv_cabinDescription)
        val cabinSelectLayout : ConstraintLayout = view.findViewById(R.id.cabinClassLayout)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.cabin_class_card, parent, false)

        return ItemViewHolder(adapterLayout)
    }



    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentCabin = dataset[position]

        holder.header.text = currentCabin.title
        holder.image.setImageResource(currentCabin.image)
        holder.title.text = currentCabin.title
        holder.description.text = currentCabin.description
        holder.cabinSelectLayout.setOnClickListener {

            //PASSING THE CURRENT CABIN AT THE GIVEN POSITION TO THE LISTENER:
            onItemClickListener?.let {
                it(currentCabin)
            }
        }
    }



    override fun getItemCount(): Int {
        return dataset.size
    }

}