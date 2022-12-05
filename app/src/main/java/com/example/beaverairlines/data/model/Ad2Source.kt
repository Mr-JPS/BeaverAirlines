package com.example.beaverairlines.data.model

import com.example.beaverairlines.R

class Ad2Source {

    fun loadAd(): List<Advertising2> {
        return listOf(
            Advertising2(R.drawable.newad1),
            Advertising2(R.drawable.newad2),
            Advertising2(R.drawable.newad3),
        )
    }
}