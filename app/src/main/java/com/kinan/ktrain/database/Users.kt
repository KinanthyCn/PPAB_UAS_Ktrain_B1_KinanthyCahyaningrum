package com.kinan.ktrain.database

import com.google.firebase.firestore.Exclude

data class Users(
//    @set:Exclude @get:Exclude @Exclude
    var uid: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val dateOfBirth: String = "",
    val role: String = "",
    val name: String = "",
    val nim : String = ""

)
