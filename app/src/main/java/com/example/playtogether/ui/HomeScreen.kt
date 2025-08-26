package com.example.playtogether.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.example.playtogether.ui.components.AppScaffold
import com.example.playtogether.viewmodel.MemberViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: MemberViewModel,
    username: String
) {
    val member by viewModel.member.collectAsState()
    AppScaffold(
        navController = navController,
        member = member
    ) { drawerState, coroutineScope ->
        HomeContentWithMemberInfo(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            viewModel = viewModel,
            username = username
        )
    }

}

@Composable
fun HomeContentWithMemberInfo(
    modifier: Modifier = Modifier,
    viewModel: MemberViewModel,
    username: String
) {
    val member by viewModel.member.collectAsState()

    // Charger les infos au démarrage
    LaunchedEffect(username) {
        viewModel.loadMember(username)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 🔹 Partie activités programmées
        Text("Mes Activités Programmées", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 Partie infos membre
        Text("Mes Informations", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(10.dp))

        if (member == null) {
            CircularProgressIndicator()
        } else {
            Text("Nom d'utilisateur : ${member!!.username}")
            Spacer(modifier = Modifier.height(8.dp))

            Text("Nom complet : ${member!!.nomComplet}")
            Spacer(modifier = Modifier.height(8.dp))

            Text("Date de naissance : ${member!!.dateNaissance}")
            Spacer(modifier = Modifier.height(8.dp))

            Text("Sexe : ${member!!.sexe}")
            Spacer(modifier = Modifier.height(8.dp))

            Text("Ville : ${member!!.ville}")
            Spacer(modifier = Modifier.height(8.dp))


        }
    }
}

