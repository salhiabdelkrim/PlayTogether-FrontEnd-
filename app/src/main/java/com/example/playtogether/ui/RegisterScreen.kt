package com.example.playtogether.ui

import android.R.attr.password
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.playtogether.model.Member
import kotlinx.coroutines.coroutineScope
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
    var sexe by remember { mutableStateOf("") }
    var ville by remember { mutableStateOf("") }
    var motDePasse by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Créer un compte", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Nom d'utilisateur") })
        OutlinedTextField(value = nomComplet, onValueChange = { nomComplet = it }, label = { Text("Nom complet") })
        OutlinedTextField(value = dateNaissance, onValueChange = { dateNaissance = it }, label = { Text("Date de naissance (YYYY-MM-DD)") })
        OutlinedTextField(value = sexe, onValueChange = { sexe = it }, label = { Text("Sexe") })
        OutlinedTextField(value = ville, onValueChange = { ville = it }, label = { Text("Ville") })
        OutlinedTextField(value = motDePasse, onValueChange = { motDePasse = it }, label = { Text("Mot de passe") }, visualTransformation = PasswordVisualTransformation())
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            coroutineScope.launch {
                // Validation date naissance
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
                dateNaissance = dateNaissance, // format : "2000-01-01"
                sexe = sexe,
                ville = ville,
                motDePasse = motDePasse
            )
            val success = registerMember(member)
            if (success) {
                // Naviguer vers l'écran suivant
                navController.navigate("login") {
                    popUpTo("register") { inclusive = true } // optionnel : retire register de la pile
                }
                } else {
                // Afficher une erreur
                errorMessage = "Erreur lors de l’inscription. Veuillez réessayer."
            }
        } }) {
            Text("S'inscrire")
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Affichage du message d'erreur s'il y en a un
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
