package com.example.playtogether.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.playtogether.model.Encounter
import com.example.playtogether.ui.components.AppScaffold
import com.example.playtogether.viewmodel.MemberViewModel

@SuppressLint("ViewModelConstructorInComposable")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageEncountersScreen(
    navController: NavHostController,
    userEncounters: List<Encounter>,
    otherEncounters: List<Encounter>,
    onEdit: (Encounter) -> Unit,
    onDelete: (Encounter) -> Unit,
    onJoin: (Encounter) -> Unit,
    viewModel: MemberViewModel,
    username: String
) {
    val member by viewModel.member.collectAsState()

    LaunchedEffect(username) {
        viewModel.loadMember(username)
    }

    AppScaffold(
        navController = navController,
        member = member
    ) { drawerState, coroutineScope ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(Modifier.height(32.dp))
            Text("Mes Rencontres", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))

            userEncounters.forEach { encounter ->
                EncounterCard(
                    encounter = encounter,
                    isMine = true,
                    onEdit = onEdit,
                    onDelete = onDelete,
                    onJoin = null
                )
            }

            Spacer(Modifier.height(24.dp))
            Text("Autres rencontres", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))

            otherEncounters.forEach { encounter ->
                EncounterCard(
                    encounter = encounter,
                    isMine = false,
                    onEdit = {},
                    onDelete = {},
                    onJoin = onJoin
                )
            }
        }
    }
}


@Composable
fun EncounterCard(
    encounter: Encounter,
    isMine: Boolean,
    onEdit: (Encounter) -> Unit,
    onDelete: (Encounter) -> Unit,
    onJoin: ((Encounter) -> Unit)?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Sport : ${encounter.sport.name}")
            Text("Date & Heure : ${encounter.dateTime}")
            Text("Lieu : ${encounter.location.name} - ${encounter.location.address}")
            Text("Prix : ${encounter.price} $")

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                if (isMine) {
                    Button(onClick = { onEdit(encounter) }) { Text("Modifier") }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { onDelete(encounter) }) { Text("Supprimer") }
                } else {
                    onJoin?.let {
                        Button(onClick = { it(encounter) }) { Text("Rejoindre") }
                    }
                }
            }
        }
    }
}
