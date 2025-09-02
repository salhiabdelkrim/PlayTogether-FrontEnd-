package com.example.playtogether

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.playtogether.model.*
import com.example.playtogether.repository.RetrofitInstance
import com.example.playtogether.repository.SportRepository
import com.example.playtogether.ui.*
import com.example.playtogether.ui.components.AppScaffold
import com.example.playtogether.ui.theme.PlayTogether
import com.example.playtogether.viewmodel.MemberViewModel
import com.example.playtogether.viewmodel.SportViewModel
import com.example.playtogether.viewmodel.SportViewModelFactory

class MainActivity : ComponentActivity() {

    @SuppressLint("ViewModelConstructorInComposable")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PlayTogether {
                val navController = rememberNavController()
                var member by remember { mutableStateOf<Member?>(null) }
                val dummySport = Sport(1, "Football", "Match de foot", 10, "soccer")
                val dummyLocation = Location(1, "Parc Lafontaine", "123 rue des Sports")
                val dummyEncounter1 = Encounter(1, dummySport, dummyLocation, "2025-08-01 14:00", 5.0)
                val dummyEncounter2 = Encounter(2, dummySport, dummyLocation, "2025-08-03 18:00", 0.0)

                val userEncounters = listOf(dummyEncounter1)
                val otherEncounters = listOf(dummyEncounter2)

                val dummyMembers = listOf(
                    Member("1", "Amira Bensalem", "2000-02-15", "Femme", "Montréal", "pass1"),
                    Member("2", "Yacine Lafleur", "1998-08-30", "Homme", "Québec", "pass2"),
                    Member("3", "Sonia Tremblay", "1990-12-10", "Femme", "Trois-Rivières", "pass3")
                )

                val currentRoute = remember { mutableStateOf("welcome") }

                // AppScaffold fournit drawerState et coroutineScope aux composables
                AppScaffold(
                    navController = navController,
                    member = member
                ) { drawerState, coroutineScope ->

                    NavHost(navController = navController, startDestination = "welcome") {

                        composable("welcome") { WelcomeScreen(navController) }
                        composable("login") { LoginScreen(navController) }
                        composable("register") { RegisterScreen(navController) }

                        // HomeScreen
                        composable("home/{username}") { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            val memberViewModel: MemberViewModel = viewModel()
                            HomeScreen(navController, memberViewModel, username)
                        }

                        // CreateEncounterScreen
                        composable("createEncounter/{username}") { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            val memberViewModel: MemberViewModel = viewModel()
                            CreateEncounterScreen(navController, memberViewModel, username)
                        }




                        // ManageEncountersScreen
                        composable("manageEncounters/{username}") { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            val memberViewModel: MemberViewModel = viewModel()
                            val member by memberViewModel.member.collectAsState()

                            LaunchedEffect(username) {
                                memberViewModel.loadMember(username)
                            }

                            if (member != null) {
                                ManageEncountersScreen(
                                    navController = navController,
                                    userEncounters = userEncounters,
                                    otherEncounters = otherEncounters,
                                    onEdit = { encounter -> println("Modifier ${encounter.encounterId}") },
                                    onDelete = { encounter -> println("Supprimer ${encounter.encounterId}") },
                                    onJoin = { encounter -> println("Rejoindre ${encounter.encounterId}") },
                                    viewModel = memberViewModel,
                                    username = username
                                )
                            }
                        }

                        // DiscoverMembersScreen
                        composable("discoverMembers/{username}") { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            val memberViewModel: MemberViewModel = viewModel()
                            val member by memberViewModel.member.collectAsState()

                            LaunchedEffect(username) {
                                memberViewModel.loadMember(username)
                            }

                            if (member != null) {
                                DiscoverMembersScreen(
                                    navController = navController,
                                    members = dummyMembers,
                                    viewModel = memberViewModel,
                                    username = username
                                )
                            }
                        }


                        // AboutSportsScreen
                        composable("aboutSports/{username}") { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            val memberViewModel: MemberViewModel = viewModel()
                            val api = RetrofitInstance.sportApi
                            val repository = SportRepository(api) // tu crées ou récupères ton repo
                            val sportViewModel: SportViewModel = viewModel(
                                factory = SportViewModelFactory(repository)
                            )

                            val member by memberViewModel.member.collectAsState()

                            LaunchedEffect(username) {
                                memberViewModel.loadMember(username)
                                sportViewModel.loadSports() // charge les sports depuis le serveur ou DB
                            }

                            if (member != null) {
                                AboutSportsScreen(
                                    navController = navController,
                                    memberViewModel = memberViewModel,  // passe le MemberViewModel
                                    sportViewModel = sportViewModel,    // passe le SportViewModel
                                    username = username
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}
