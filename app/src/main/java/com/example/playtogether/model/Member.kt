package com.example.playtogether.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class Member(
    val username: String,
    val nomComplet: String,
    val dateNaissance: String, // ou LocalDate si tu veux faire des calculs
    val sexe: String,
    val ville: String,
    val motDePasse: String



)
