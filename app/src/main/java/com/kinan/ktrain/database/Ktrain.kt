package com.kinan.ktrain.database

import com.google.firebase.firestore.Exclude

data class Ktrain(
//    @set:Exclude @get:Exclude @Exclude
    var id: String = "",
    val departure : String = "",
    val destination : String = "",
    val train : String ="",
    val classTrain : String ="",

)

