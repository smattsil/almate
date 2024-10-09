package com.example.almate

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.almate.features.auth.presentation.LoginScreen
import com.example.almate.features.home.presentation.HomeListDetailScreen
import com.example.almate.features.home.presentation.components.HomeViewModel
import com.example.almate.features.home.presentation.components.SubjectViewModel
import com.example.almate.features.rankings.presentation.RankingsScreen
import com.example.almate.features.rankings.presentation.RankingsViewModel
import com.example.almate.features.profile.presentation.ProfileScreen
import com.example.almate.features.profile.presentation.ProfileViewModel
import com.example.almate.features.tools.presentation.ToolsScreen
import com.example.almate.features.tools.presentation.ToolsViewModel
import com.example.almate.presentation.theme.proximaNovaFamily
import kotlinx.serialization.Serializable

@Serializable data object Auth
@Serializable data object Main

@Composable
fun AlmateApp(
    isLoggedIn: Boolean
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        val innerPadding = it
        val almateNavController = rememberNavController()
        AlmateNavigation(
            isLoggedIn = isLoggedIn,
            navController = almateNavController
        )
    }
}

@Composable
fun AlmateNavigation(
    isLoggedIn: Boolean,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Main else Auth,
        modifier = modifier
    ) {
        composable<Auth> {
            LoginScreen()
        }
        composable<Main> {
            val mainNavController = rememberNavController()
            MainApp(mainNavController)
        }
    }
}

// Routes
@Serializable data object Home
@Serializable data object Rankings
@Serializable data object Tools
@Serializable data object Profile

enum class AppDestinations(
    val label: String,
    val route: Any,
    val unselectedIcon: Int,
    val selectedIcon: Int
) {
    HOME(
       label =  "Home",
        route = Home,
        unselectedIcon = R.drawable.home_24dp_e8eaed_fill0_wght400_grad0_opsz24,
        selectedIcon = R.drawable.home_24dp_e8eaed_fill1_wght400_grad0_opsz24
    ),
    LEADERBOARD(
        label =  "Rankings",
        route = Rankings,
        unselectedIcon = R.drawable.leaderboard_24dp_e8eaed_fill0_wght400_grad0_opsz24,
        selectedIcon = R.drawable.leaderboard_24dp_e8eaed_fill1_wght400_grad0_opsz24
    ),
    TOOLS(
        label =  "Tools",
        route = Tools,
        unselectedIcon = R.drawable.handyman_24dp_e8eaed_fill0_wght400_grad0_opsz24,
        selectedIcon = R.drawable.handyman_24dp_e8eaed_fill1_wght400_grad0_opsz24
    ),
    PROFILE(
        label =  "Profile",
        route = Profile,
        unselectedIcon = R.drawable.person_24dp_e8eaed_fill0_wght400_grad0_opsz24,
        selectedIcon = R.drawable.person_24dp_e8eaed_fill1_wght400_grad0_opsz24
    )
}

@Composable
fun MainApp(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach { destination ->
                item(
                    icon = {
                        val selected = currentDestination?.hierarchy?.any { it.hasRoute(destination.route::class) } == true
                        Icon(
                            painter = if (selected) painterResource(destination.selectedIcon) else painterResource(destination.unselectedIcon),
                            contentDescription = destination.label
                        )
                    },
                    label = {
                        Text(
                            text = destination.label,
                            fontFamily = proximaNovaFamily,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    selected = currentDestination?.hierarchy?.any { it.hasRoute(destination.route::class) } == true,
                    onClick = {
                        navController.navigate(destination.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) {
        MainNavigation(navController = navController)
    }
}

@Composable
fun MainNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Home,
        modifier = modifier.fillMaxSize()
    ) {
        composable<Home> {
            val homeViewModel: HomeViewModel = hiltViewModel()
            val subjectViewModel: SubjectViewModel = hiltViewModel()
            HomeListDetailScreen(
                homeViewModel,
                subjectViewModel,
                onProfilePictureClick = {
                    navController.navigate(Profile) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        composable<Rankings> {
            val rankingsViewModel: RankingsViewModel = hiltViewModel()
            RankingsScreen(rankingsViewModel)
        }
        composable<Tools> {
            val toolsViewModel: ToolsViewModel = hiltViewModel()
            ToolsScreen()
        }
        composable<Profile> {
            val profileViewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(profileViewModel = profileViewModel)
        }
    }
}
