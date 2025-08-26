package com.example.playtogether.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.playtogether.model.Member
import com.example.playtogether.ui.components.AppScaffold
import com.example.playtogether.viewmodel.MemberViewModel

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverMembersScreen(
    navController: NavHostController,
    members: List<Member>,
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
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 64.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            items(members) { m ->
                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Nom : ${m.nomComplet}", style = MaterialTheme.typography.titleMedium)
                        Text("Âge : ${calculateAge(m.dateNaissance)} ans")
                        Text("Sexe : ${m.sexe}")
                        Text("Ville : ${m.ville}")
                    }
                }
            }
        }
    }
}

