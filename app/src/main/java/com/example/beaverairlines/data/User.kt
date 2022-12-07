package com.example.beaverairlines.data

import com.google.firebase.firestore.DocumentId

//DATA CLASS FOR USERS TO HAND OVER TO FIREBASE:

data class User (
    @DocumentId
    val userId : String = " ",
    val fullName : String = " ",
    val firstName : String = "",
    val lastName : String = " ",
    val email : String = " ",
    val birthDate: String = " ",
    val birthPlace: String = " ",
    val gender: String = " ",
    val nationality: String = " ",
    val country: String = " ",
    val passportNbr: String = " ",
    val mileHighClubNbr : String = " "
)

