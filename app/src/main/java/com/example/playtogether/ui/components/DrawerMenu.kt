package com.example.playtogether.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DrawerContent(
    username: String,
    onDestinationClicked: (String) -> Unit
) {
    val destinations = listOf(
        "home/$username" to "Accueil",
        "createEncounter/$username" to "Créer une rencontre",
        "manageEncounters/$username" to "Gérer mes rencontres",
        "discoverMembers/$username" to "Découvrir des membres",
        "aboutSports/$username" to "À propos des sports"
    )

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxHeight()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Connecté : $username", style = MaterialTheme.typography.h6, color = Color.Black)
            Spacer(modifier = Modifier.height(24.dp))
            destinations.forEach { (route, title) ->
                TextButton(onClick = { onDestinationClicked(route) }) {
                    Text(title, style = MaterialTheme.typography.body1, color = Color.Black)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
