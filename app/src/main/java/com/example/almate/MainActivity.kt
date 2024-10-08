package com.example.almate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.almate.features.auth.presentation.LoginScreen
import com.example.almate.features.tools.presentation.components.MessageTextField
import com.example.almate.presentation.ErrorScreen
import com.example.almate.presentation.LoadingScreen
import com.example.almate.presentation.theme.AlmateTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        setContent {

            val viewModel = hiltViewModel<MainActivityViewModel>()

            splashScreen.setKeepOnScreenCondition {
                when (viewModel.isLoggedIn) {
                    null -> true
                    else -> false
                }
            }

            AlmateTheme { viewModel.isLoggedIn?.let { AlmateApp(isLoggedIn = it) } }

        }
    }
}

@Serializable data object Auth
@Serializable data object Main

@Composable
fun AlmateApp(
    isLoggedIn: Boolean
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        val almateNavController = rememberNavController()
        AlmateNavigation(
            isLoggedIn = isLoggedIn,
            navController = almateNavController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun AlmateNavigation(
    isLoggedIn: Boolean,
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Main else Auth,
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
