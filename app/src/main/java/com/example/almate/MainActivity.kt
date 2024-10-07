package com.example.almate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.almate.features.auth.presentation.LoginScreen
import com.example.almate.presentation.ErrorScreen
import com.example.almate.presentation.LoadingScreen
import com.example.almate.presentation.theme.AlmateTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val viewModel = hiltViewModel<MainActivityViewModel>()
            val credentialsFlow = viewModel.credentialsFlow
            val navController = rememberNavController()

            LaunchedEffect(Unit) {
                credentialsFlow.collect { credentials ->
                    val isLoggedIn = credentials.username.isNotBlank()
                    if (isLoggedIn) {
                        viewModel.appState = AppState.Main
                    } else {
                        viewModel.appState = AppState.Auth
                    }
                }
            }

            AlmateTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    when (viewModel.appState) {
                        is AppState.Loading -> LoadingScreen(modifier = Modifier.padding(innerPadding))
                        is AppState.Auth -> LoginScreen(modifier = Modifier.padding(innerPadding))
                        is AppState.Main -> AlmateApp(navController = navController, modifier = Modifier.padding(innerPadding))
                        is AppState.Error -> ErrorScreen(onClick = {})
                    }
                }
            }

        }
    }
}
