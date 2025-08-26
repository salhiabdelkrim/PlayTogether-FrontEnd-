package com.example.playtogether.model

import com.google.gson.annotations.SerializedName

data class Encounter(
    @SerializedName("encounterId") val encounterId: Long,
    @SerializedName("sport") val sport: Sport,
    @SerializedName("location") val location: Location,
    @SerializedName("dateTime") val dateTime: String,
    @SerializedName("price") val price: Double

)