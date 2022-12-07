package com.example.beaverairlines.data.model

import com.example.beaverairlines.R

//REPO FOR PROVIDING AD IMAGES IN BOOKING FRAGMENT

class Ad2Source {

    fun loadAd(): List<Advertising2> {
        return listOf(
            Advertising2(R.drawable.newad1),
            Advertising2(R.drawable.newad2),
            Advertising2(R.drawable.newad3),
        )
    }
}