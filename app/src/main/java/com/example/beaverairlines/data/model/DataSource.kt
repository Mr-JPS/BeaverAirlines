package com.example.beaverairlines.data.model

import com.example.beaverairlines.R

class DataSource {

    fun loadDestination() : List<Destination>{
        return listOf(
            Destination(
                "Amalfi Coast",
                R.drawable.amalficoastitaly,
            ),
            Destination(
                "Amsterdam",
                R.drawable.amsterdamnetherlands,
            ),
            Destination(
                "Bali",
                R.drawable.baliindonesia,
            ),
            Destination(
                "Cancun",
                R.drawable.cancunmexico,
            ),
            Destination(
                "Florence",
                R.drawable.florenceitaly,
            ),
            Destination(
                "London",
                R.drawable.londonengland,
            ),
            Destination(
                "New York City",
                R.drawable.newyorkcityusa,
            ),
            Destination(
                "Paris",
                R.drawable.parisfrance,
            ),
            Destination(
                "San Francisco",
                R.drawable.sanfranciscocalifornia,
            ),
            Destination(
                "Santorini",
                R.drawable.santorinigreece,
            ),
            Destination(
                "Sydney",
                R.drawable.sydneyaustralia,
            ),
            Destination(
                "Tokyo",
                R.drawable.tokyojapan,
            ),
            Destination(
                "Dubai",
                R.drawable.dubaiunitedarabemirates,
            ),
            Destination(
                "Barcelona",
                R.drawable.barcelonaspain,
            ),
        )
    }
}