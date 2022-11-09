package com.example.beaverairlines.data.model

import com.example.beaverairlines.R

class AdSource {

    fun loadAd() : List<Advertising>{
        return listOf(
            Advertising(
                "Meet Your Crew",
                R.drawable.banner1
            ),
            Advertising(
                "On\nBoard",
                R.drawable.banner5
            ),
            Advertising(
                "Mile-High Club",
                R.drawable.banner2
            ),
            Advertising(
                "Merry Beaver",
                R.drawable.banner3
            ),
            Advertising(
                "About Beaver\nInc",
                R.drawable.banner4
            ),
        )
    }
}