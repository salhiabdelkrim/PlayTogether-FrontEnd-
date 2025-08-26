package com.example.playtogether.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.playtogether.ui.components.AppScaffold
import com.example.playtogether.viewmodel.MemberViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEncounterScreen(
    navController: NavController,
    viewModel: MemberViewModel,
    username: String
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val member by viewModel.member.collectAsState()
    LaunchedEffect(username) {
        viewModel.loadMember(username)
    }

    val sports = listOf("Football", "Basketball", "Tennis", "Volleyball", "Running")
    var selectedSport by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }

    // ✅ Utilisation d'AppScaffold avec drawer et TopBar
    AppScaffold(
        navController = navController,
        member = member
    ) { drawerState, coroutineScope ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Titre
            Text("Créer une rencontre", style = MaterialTheme.typography.headlineMedium)

            // Dropdown pour le sport
            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = selectedSport,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Sport") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    sports.forEach { sport ->
                        DropdownMenuItem(
                            text = { Text(sport) },
                            onClick = {
                                selectedSport = sport
                                expanded = false
                            }
                        )
                    }
                }
            }

            // Date picker
            Button(onClick = {
                DatePickerDialog(
                    context,
                    { _, year, month, day ->
                        selectedDate = "$day/${month + 1}/$year"
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }) {
                Text(if (selectedDate.isEmpty()) "Choisir une date" else "Date : $selectedDate")
            }

            // Time picker
            Button(onClick = {
                TimePickerDialog(
                    context,
                    { _, hour, minute ->
                        selectedTime = String.format("%02d:%02d", hour, minute)
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()
            }) {
                Text(if (selectedTime.isEmpty()) "Choisir une heure" else "Heure : $selectedTime")
            }

            // Adresse
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Adresse du terrain") },
                modifier = Modifier.fillMaxWidth()
            )

            // Bouton trouver adresse
            Button(
                onClick = {
                    val gmmIntentUri =
                        Uri.parse("https://www.google.com/maps/search/?api=1&query=terrain+de+sports+près+de+moi")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    try {
                        context.startActivity(mapIntent)
                    } catch (e: ActivityNotFoundException) {
                        val browserIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        context.startActivity(browserIntent)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Trouver une adresse")
            }

            // Prix
            OutlinedTextField(
                value = price,
                onValueChange = {
                    if (it.matches(Regex("^\\d*\\.?\\d*$"))) price = it
                },
                label = { Text("Montant à payer (optionnel)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Bouton créer rencontre
            Button(
                onClick = {
                    if (selectedSport.isNotEmpty() && selectedDate.isNotEmpty() &&
                        selectedTime.isNotEmpty() && address.isNotEmpty()
                    ) {
                        Toast.makeText(context, "Rencontre créée !", Toast.LENGTH_SHORT).show()
                        navController.navigate("home/${member?.username ?: ""}")
                    } else {
                        Toast.makeText(
                            context,
                            "Veuillez remplir tous les champs obligatoires",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Créer la rencontre")
            }
        }
    }
}

