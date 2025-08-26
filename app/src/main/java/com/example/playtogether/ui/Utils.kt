package com.example.playtogether.ui

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.playtogether.model.Member
import com.example.playtogether.network.ApiClient
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import android.util.Log

@RequiresApi(Build.VERSION_CODES.O)
fun calculateAge(dateNaissance: String): Int {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val birthDate = LocalDate.parse(dateNaissance, formatter)
        val now = LocalDate.now()
        Period.between(birthDate, now).years
    } catch (e: Exception) {
        0
    }
}

suspend fun registerMember(member: Member): Boolean {
    return try {
        val response: HttpResponse = ApiClient.client.post("http://192.168.1.46:8080/api/members/register") {
            contentType(ContentType.Application.Json)
            setBody(member)
        }
        Log.d("REGISTER", "Réponse: ${response.status}")
        Log.d("REGISTER", "Contenu: ${response.bodyAsText()}")
        response.status == HttpStatusCode.OK
    } catch (e: Exception) {
        println("Erreur d'enregistrement : ${e.message}")
        false
    }
}

suspend fun loginMember(username: String, password: String): Boolean {
    return try {
        val response: HttpResponse = ApiClient.client.post("http://192.168.1.46:8080/api/members/login") {
            contentType(ContentType.Application.Json)
            setBody(mapOf("username" to username, "password" to password))
        }
        response.status == HttpStatusCode.OK
    } catch (e: Exception) {
        println("Erreur de connexion : ${e.message}")
        false
    }
}
