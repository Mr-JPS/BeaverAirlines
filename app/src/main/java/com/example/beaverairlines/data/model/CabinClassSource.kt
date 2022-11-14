package com.example.beaverairlines.data.model

import com.example.beaverairlines.R

class CabinClassSource {

    fun loadCabinClass() : List<CabinClass>{
        return listOf(
            CabinClass(
                R.drawable.img_8897,
                "FIRST CLASS",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore"
            ),
            CabinClass(
                R.drawable.img_8896,
                "BUSINESS CLASS",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore"
            ),
            CabinClass(
                R.drawable.img_8898,
                "PREMIUM ECO CLASS",
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore"
            )
        )
    }
}
