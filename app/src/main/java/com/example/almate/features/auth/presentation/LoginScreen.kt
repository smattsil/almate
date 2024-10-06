package com.example.almate.features.auth.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.almate.R
import com.example.almate.presentation.theme.cardBackgroundColor
import com.example.almate.presentation.theme.proximaNovaFamily

@Preview(showBackground = true)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val credentials = loginViewModel.credentials
    if (loginViewModel.loginState is LoginState.Error) {
        Dialog(
            onDismissRequest = { loginViewModel.loginState = null }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(cardBackgroundColor)
                    .border(2.dp, Color.Black.copy(alpha = 0.10f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 18.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "Your credentials are invalid. Check if you have entered the correct details and try again."
                )
                TextButton(
                    onClick = { loginViewModel.loginState = null }
                ) {
                    Text(
                        text = "OK",
                        fontFamily = proximaNovaFamily
                    )
                }
            }
        }
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .imePadding()
    ) {
        Icon(
            painterResource(R.drawable.almate),
            contentDescription = null,
            modifier = Modifier.size(144.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            label = {
                Text(
                    text = "School",
                    fontFamily = proximaNovaFamily
                )
                    },
            placeholder = {
                Text(
                    text = "i.e. cbcs",
                    fontStyle = FontStyle.Italic,
                    color = Color.White.copy(alpha = 0.5f)
                )
            },
            enabled = loginViewModel.loginState != LoginState.Loading,
            value = credentials.school,
            onValueChange = { loginViewModel.onSchoolChange(it) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            leadingIcon = { Icon(painterResource(R.drawable.school_24dp_e8eaed_fill1_wght400_grad0_opsz24), contentDescription = null) },
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            label = {
                Text(
                    text = "Username",
                    fontFamily = proximaNovaFamily
                )
            },
            placeholder = {
                Text(
                    text = "i.e. jason.2025",
                    fontStyle = FontStyle.Italic,
                    color = Color.White.copy(alpha = 0.5f)
                )
            },
            enabled = loginViewModel.loginState != LoginState.Loading,
            value = credentials.username,
            onValueChange = { loginViewModel.onUsernameChange(it)},
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            leadingIcon = { Icon(painterResource(R.drawable.person_24dp_e8eaed_fill1_wght400_grad0_opsz24), contentDescription = null) },
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            label = {
                Text(
                    text = "Password",
                    fontFamily = proximaNovaFamily
                )
            },
            enabled = loginViewModel.loginState != LoginState.Loading,
            value = credentials.password,
            onValueChange = { loginViewModel.onPasswordChange(it) },
            shape = RoundedCornerShape(12.dp),
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = { Icon(painterResource(R.drawable.password_24dp_e8eaed_fill1_wght400_grad0_opsz24), contentDescription = null) },
            keyboardActions = KeyboardActions(
                onDone = { loginViewModel.login() }
            ),
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { loginViewModel.login() },
            enabled = listOf(credentials.school, credentials.username, credentials.password).all { it.isNotEmpty() },
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(2.dp, Color.Black.copy(alpha = 0.1f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            if (loginViewModel.loginState is LoginState.Loading) {
                CircularProgressIndicator(
                    strokeWidth = 2.dp,
                    color = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            } else {
                Text(
                    text = "Log in",
                    fontWeight = FontWeight.Bold,
                    fontFamily = proximaNovaFamily
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = { },
            enabled = loginViewModel.loginState != LoginState.Loading
        ) {
            Text(
                text = "Encountering issues?",
                fontWeight = FontWeight.Bold,
                fontFamily = proximaNovaFamily
            )
        }
    }
}
