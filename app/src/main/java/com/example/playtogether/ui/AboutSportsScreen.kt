package com.example.playtogether.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.playtogether.R
import com.example.playtogether.model.Sport
import com.example.playtogether.ui.components.AppScaffold
import com.example.playtogether.viewmodel.MemberViewModel
import com.example.playtogether.viewmodel.SportViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutSportsScreen(
    navController: NavHostController,
    memberViewModel: MemberViewModel,
    sportViewModel: SportViewModel,
    username: String
) {
    val member by memberViewModel.member.collectAsState()
    val sportsList by sportViewModel.sports.collectAsState()

    LaunchedEffect(username) {
        memberViewModel.loadMember(username)
        sportViewModel.loadSports()  // récupère les sports depuis le serveur
    }

    AppScaffold(navController = navController, member = member) { drawerState, coroutineScope ->
        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp)) {
            items(sportsList) { sport ->
                SportCard(sport = sport)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@SuppressLint("DiscouragedApi")
@Composable
fun SportCard(sport: Sport) {
    val context = LocalContext.current

    // Récupérer l'image
    val imageResId = context.resources.getIdentifier(
        sport.imageName, "drawable", context.packageName
    )

    // Si introuvable, on utilise une image par défaut
    val painter = if (imageResId != 0) {
        painterResource(id = imageResId)
    } else {
        painterResource(id = R.drawable.soccer) // mets une icône dans drawable
    }

    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painter,
                contentDescription = sport.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = sport.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = sport.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
