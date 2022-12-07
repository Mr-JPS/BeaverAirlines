package com.example.beaverairlines.data.model

import com.example.beaverairlines.R


//REPO FOR PROVIDING AD IMAGES IN BOOKING FRAGMENT

class AdSource {

    fun loadAd() : List<Advertising>{
        return listOf(
            Advertising(
                "Meet Your Crew",
                R.drawable.banner1,
                "Who you'll see onboard",
                R.drawable.image_2,
                "Our onboard heroes, keeping you safe, and delivering our famous service in style. On your journey, you'll meet Cabin Crew, Cabin Service Supervisors and Flight Service Managers - look out for a purple shirt or silver tie to spot who the manager is on your next flight with us. \n\nOur pilots have the best seat in the house, leading our operation every day and working hard to get you to your destination safely. You'll find two different ranks of pilot on board, and you can tell who's who by the stripes on the shoulders of their uniform - your Captain will have four stripes, and Senior First Officers will have three."

            ),
            Advertising(
                "On\nBoard",
                R.drawable.banner5,
                "The dining experience",
                R.drawable.onboardservice,
                "Our menus are designed to compliment your departure time, origin and destination, served throughout your flight on designer linens, plates and cutlery. A great selection of premium wine, beer, spirits and soft drinks can be served at your seat, at the bar, or in The Loft. \n\nChoose from our four-course menu, including a selection of starters, mains, desserts and topped off with a delicious cheese course served with port. Enjoy with a selection of carefully curated wines in partnership with London-based fine wine merchant Jeroboams."

            ),
            Advertising(
                "Mile-High Club",
                R.drawable.banner2,
                "Rewards with Beaver's Mile High Club",
                R.drawable.milehighpic,
                "Flying with us is the fastest way to earn points. The more you fly and the more you spend, the more you'll earn. Your cabin, ticket type and membership tier all contribute, but it’s far from the only way.\n\nIn conjunction with Beaver Inc., you can supercharge your points balance when you spend on everything from your weekly shop to your next trip with our partner airlines. Catch every chance to make your next adventure happen."

            ),
            Advertising(
                "Merry Beaver",
                R.drawable.banner3,
                "Festive jumpers are in at Beaver Airlines",
                R.drawable.christmasad,
                "Beaver Airline is bringing the magic of Christmas to 35,000 feet by introducing its very own Christmas jumper for the festive period. To celebrate Save the Children’s Christmas Jumper Day on the 14th December, the jumper will be worn by all the cabin crew and airport teams over Christmas Eve, Christmas Day and Boxing Day.\n\nAll funds raised from the airline’s onboard collections will be diverted to Save the Children and Beaver Airlines staff will be undertaking a number of activities on the day to raise money for the cause."
            ),
            Advertising(
                "About Beaver\nInc",
                R.drawable.banner4,
                "Our story",
                R.drawable.aboutpic,
                "It’s easy to look to things like innovative products and services, glamorous destinations and charismatic crew in bright red uniforms as the reasons for our success. But these are just part of our story. The personality and language of our brand plays a major role too, and to get a grasp on why, it’s useful to look at our past.\n\nWe gave people a choice. A bright red, fun, friendly, fabulous choice that made travel attainable for everyone..."
            ),
        )
    }
}