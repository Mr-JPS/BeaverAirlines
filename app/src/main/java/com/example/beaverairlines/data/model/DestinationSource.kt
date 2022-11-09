package com.example.beaverairlines.data.model

import com.example.beaverairlines.R

class DestinationSource {

    fun loadDestination() : List<Destination>{
        return listOf(
            Destination(
                "New York City",
                "USA",
                R.drawable.newyorkcityusa,
            ),
            Destination(
                "Bali",
                "Indonesia",
                R.drawable.baliindonesia,
            ),
            Destination(
                "Cancun",
                "Mexico",
                R.drawable.cancunmexico,
            ),
            Destination(
                "Florence",
                "Italy",
                R.drawable.florenceitaly,
            ),
            Destination(
                "London",
                "Englang",
                R.drawable.londonengland,
            ),
            Destination(
                "Paris",
                "France",
                R.drawable.parisfrance,
            ),
            Destination(
                "San Francisco",
                "USA",
                R.drawable.sanfranciscocalifornia,
            ),
            Destination(
                "Santorini",
                "Greece",
                R.drawable.santorinigreece,
            ),
            Destination(
                "Sydney",
                "Australia",
                R.drawable.sydneyaustralia,
            ),
            Destination(
                "Tokyo",
                "Japan",
                R.drawable.tokyojapan,
            ),
            Destination(
                "Dubai",
                "UAE",
                R.drawable.dubaiunitedarabemirates,
            ),
            Destination(
                "Barcelona",
                "Spain",
                R.drawable.barcelonaspain,
            ),
        ).shuffled()
    }
}