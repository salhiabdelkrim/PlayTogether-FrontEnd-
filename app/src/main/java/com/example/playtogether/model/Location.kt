package com.example.playtogether.model

import com.google.gson.annotations.SerializedName


data class Location(
    @SerializedName("locationId") val locationId: Long,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String
)