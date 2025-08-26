package com.example.playtogether.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.playtogether.model.Member
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    navController: NavController,
    member: Member?,
    content: @Composable (drawerState: DrawerState, coroutineScope: CoroutineScope) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    if (member != null) {
        // ✅ Menu visible uniquement si utilisateur connecté
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(member.username) { route ->
                    scope.launch { drawerState.close() }
                    navController.navigate(route)
                }
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Menu") },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        }
                    )
                }
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    content(drawerState, scope)
                }
            }
        }
    } else {
        // ✅ Pas de menu si pas connecté
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("PlayTogether") }
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                content(drawerState, scope)
            }
        }
    }
}
