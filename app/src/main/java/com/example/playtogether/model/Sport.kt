package com.example.playtogether.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName


@Entity(tableName = "Sport")
data class Sport(
    @SerializedName("sportId") val sportId: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("numberOfPlayers") val numberOfPlayers: Int,
    @SerializedName("imageName") val imageName: String
)