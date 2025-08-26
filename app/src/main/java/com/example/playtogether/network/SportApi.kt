package com.example.playtogether.network

import com.example.playtogether.model.Sport
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SportApi {
    @GET("api/sports")
    suspend fun getSports(): List<Sport>

    @POST("api/sports")
    suspend fun addSport(@Body sport: Sport): Sport
}
