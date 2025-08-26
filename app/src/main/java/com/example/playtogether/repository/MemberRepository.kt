package com.example.playtogether.repository

import com.example.playtogether.model.Member
import com.example.playtogether.network.ApiClient
import io.ktor.client.request.*
import io.ktor.client.call.*

object MemberRepository {
    suspend fun getMemberByUsername(username: String): Member {
        return ApiClient.client.get("http://192.168.1.46:8080/api/members/username/$username")
            .body()
    }
}