package com.example.playtogether.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.playtogether.model.Member
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var nomComplet by remember { mutableStateOf("") }
    var dateNaissance by remember { mutableStateOf("") }
    var sexe by remember { mutableStateOf("") } // valeur par défaut
    var ville by remember { mutableStateOf("") }
    var motDePasse by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val optionsSexe = listOf("Homme", "Femme")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Créer un compte", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nom d'utilisateur") }
        )

        OutlinedTextField(
            value = nomComplet,
            onValueChange = { nomComplet = it },
            label = { Text("Nom complet") }
        )

        OutlinedTextField(
            value = dateNaissance,
            onValueChange = { dateNaissance = it },
            label = { Text("Date de naissance (YYYY-MM-DD)") },
            singleLine = true
        )

        // Groupe de boutons radio pour le sexe
        Spacer(modifier = Modifier.height(12.dp))
        Text("Sexe", style = MaterialTheme.typography.bodyLarge)
        optionsSexe.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = (sexe == option),
                    onClick = { sexe = option }
                )
                Text(option, modifier = Modifier.padding(start = 8.dp))
            }
        }

        OutlinedTextField(
            value = ville,
            onValueChange = { ville = it },
            label = { Text("Ville") }
        )

        OutlinedTextField(
            value = motDePasse,
            onValueChange = { motDePasse = it },
            label = { Text("Mot de passe") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            coroutineScope.launch {
                // Vérification des champs obligatoires
                if (username.isEmpty() || nomComplet.isEmpty() || dateNaissance.isEmpty() || sexe.isEmpty() || ville.isEmpty() || motDePasse.isEmpty()) {
                    errorMessage = "Veuillez remplir tous les champs"
                    return@launch
                }

                // Vérification stricte du format YYYY-MM-DD
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val isDateValid = try {
                    LocalDate.parse(dateNaissance, formatter)
                    true
                } catch (e: DateTimeParseException) {
                    false
                }

                if (!isDateValid) {
                    errorMessage = "La date de naissance doit être au format YYYY-MM-DD"
                    return@launch
                }

                val member = Member(
                    username = username,
                    nomComplet = nomComplet,
                    dateNaissance = dateNaissance,
                    sexe = sexe,
                    ville = ville,
                    motDePasse = motDePasse
                )

                val success = registerMember(member)
                if (success) {
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                } else {
                    errorMessage = "Erreur lors de l’inscription. Veuillez réessayer."
                }
            }
        }) {
            Text("S'inscrire")
        }

        Spacer(modifier = Modifier.height(8.dp))

        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { navController.popBackStack() }) {
            Text("Retour à la connexion")
        }
    }
}
