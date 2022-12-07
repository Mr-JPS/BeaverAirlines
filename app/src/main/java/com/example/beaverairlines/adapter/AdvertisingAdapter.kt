package com.example.beaverairlines.adapter

import android.animation.ObjectAnimator
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.beaverairlines.R
import com.example.beaverairlines.data.model.Advertising
import kotlinx.android.synthetic.main.book3_card.*

// THIS ADAPTER MANAGES THE FUNCTIONALITIES FOR THE ADs IN THE DASHBOARD FRAGMENT

class AdvertisingAdapter (
    private val dataset: List<Advertising>
        ): RecyclerView.Adapter<AdvertisingAdapter.ItemViewHolder>()  {

    class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.tv_title)
        val banner: ImageView = view.findViewById(R.id.iv_banner)
        val detailCard: CardView = view.findViewById(R.id.AD_detailCard)
        val bannerCard: CardView = view.findViewById(R.id.AD_bannerCard)
        val detailTitle: TextView = view.findViewById(R.id.AD_detailCard_title)
        val detailPic: ImageView = view.findViewById(R.id.AD_detailCard_pic)
        val detailDescription: TextView = view.findViewById(R.id.AD_detailCard_description)
        val detailCloseBttn: TextView = view.findViewById(R.id.Ad_detailCard_closeBttn)
        val adMainCard: ConstraintLayout = view.findViewById(R.id.AD_mainCard)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.advertising_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }



    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
       val ad = dataset[position]

        val adAnimatorIN = ObjectAnimator.ofFloat(holder.detailCard, View.TRANSLATION_X,-1000f,0f)
        adAnimatorIN.duration = 300
        val adAnimatorOUT = ObjectAnimator.ofFloat(holder.detailCard, View.TRANSLATION_X,0f,-1000f)
        adAnimatorOUT.duration = 300

        holder.title.text = ad.title
        holder.banner.setImageResource(ad.image)
        holder.detailTitle.text = ad.cardTitle
        holder.detailPic.setImageResource(ad.cardPic)
        holder.detailDescription.text = ad.cardDescription

        holder.bannerCard.setOnClickListener {
            if (holder.detailCard.visibility == View.GONE){
                adAnimatorIN.start()
                TransitionManager.beginDelayedTransition(holder.adMainCard, AutoTransition())
                holder.detailCard.visibility = View.VISIBLE

            } else {
                adAnimatorOUT.start()
                TransitionManager.beginDelayedTransition(holder.adMainCard, AutoTransition())
                holder.detailCard.visibility = View.GONE
            }
        }


        holder.detailCloseBttn.setOnClickListener {
            TransitionManager.beginDelayedTransition(holder.adMainCard, AutoTransition())
            adAnimatorOUT.start()
            holder.detailCard.visibility = View.GONE
        }
    }



    override fun getItemCount(): Int {
        return dataset.size
    }
}