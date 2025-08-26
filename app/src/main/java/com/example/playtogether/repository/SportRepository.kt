package com.example.playtogether.repository


import com.example.playtogether.model.Sport
import com.example.playtogether.network.SportApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class SportRepository(private val api: SportApi) {

    suspend fun getSports(): List<Sport> {
        return api.getSports() // récupère les sports depuis le serveur
    }

    suspend fun addSport(sport: Sport): Sport {
        return api.addSport(sport)
    }
}



object RetrofitInstance {
    val sportApi: SportApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.1.46:8080/") // URL du serveur
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SportApi::class.java)
    }
}
