package com.example.beaverairlines.data

import com.google.firebase.firestore.DocumentId

data class User (
    @DocumentId
    val userId : String = " ",
    val fullName : String = " ",
    val firstName : String = "",
    val lastName : String = " ",
    val email : String = " ",
    val birthdate: String = " ",
    val street: String = " ",
    val houseNbr: String = " ",
    val zipCode: String = " ",
    val city: String = " ",
    val country: String = " ",
    val passportNbr: String = " ",
    val mileHighClubNbr : String = " "
)