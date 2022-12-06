package com.example.beaverairlines.data.model

import com.example.beaverairlines.R

class CabinClassSource {

    fun loadCabinClass() : List<CabinClass>{
        return listOf(
            CabinClass(
                R.drawable.img_8898,
                "PREMIUM ECONOMY CLASS",
                "- Large Leather\n- Standard legroom\n- Select Premium seat any time\n- 13.3 inch Entertainment Screen"
            ),
            CabinClass(
                R.drawable.img_8896,
                "BUSINESS CLASS",
                "- Extra large Leather\n- Extra legroom\n- Select Premium seat any time\n- 13.3 inch Entertainment Screen"
            ),
            CabinClass(
                R.drawable.img_8897,
                "FIRST CLASS",
                "- Fully flat bed\n- Extra legroom\n- Select Upper Class seat any time\n- Suite with Good Night Service"
            )
        )
    }
}
