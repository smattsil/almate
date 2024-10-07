package com.example.almate.features.profile.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.almate.R
import com.example.almate.features.profile.data.GetAttendancesResponse
import com.example.almate.features.profile.data.GetPersonalInfoResponse
import com.example.almate.features.profile.presentation.components.LogOutDialog
import com.example.almate.presentation.ErrorScreen
import com.example.almate.presentation.theme.cardBackgroundColor
import com.example.almate.presentation.theme.proximaNovaFamily

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    scrollState: ScrollState = rememberScrollState(),
    onLogOutNavigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            ProfileTopAppBar()
        }
    ) { innerPadding ->
        when (profileViewModel.profileState) {
            is ProfileState.Loading -> ProfileSkeletonScreen(modifier = modifier.padding(innerPadding))
            is ProfileState.Success ->
                Profile(
                    onLogOutNavigate = onLogOutNavigate,
                    profileViewModel = profileViewModel,
                    scrollState = scrollState,
                    modifier = modifier.padding(innerPadding)
                )
            is ProfileState.Error -> ErrorScreen(onClick = { profileViewModel.fetchData() })
        }
    }
}

@Composable
fun Profile(
    onLogOutNavigate: () -> Unit,
    profileViewModel: ProfileViewModel,
    scrollState: ScrollState,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        if (profileViewModel.showConfirmDialog) {
            LogOutDialog(
                onDismissRequest = { profileViewModel.showConfirmDialog = false },
                onButtonClick = {
                    profileViewModel.logout(it)
                    profileViewModel.showConfirmDialog = false
                    onLogOutNavigate()
                }
            )
        }
        if (profileViewModel.showUpdateProfilePictureDialog) {
            ChangeProfilePictureDialog(
                onDismissRequest = {
                    profileViewModel.showUpdateProfilePictureDialog = false
                },
                onConfirmation = {
                    profileViewModel.updateProfilePicture(it)
                    profileViewModel.showUpdateProfilePictureDialog = false
                },
                textValue = profileViewModel.supabaseUser!!.profilePicture,
                onValueChange = { profileViewModel.onPfpValueChange(it) }
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(82.dp)
                .clip(CircleShape)
                .clickable { profileViewModel.showUpdateProfilePictureDialog = true }
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(profileViewModel.supabaseUser!!.profilePicture)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    Color.Black.copy(0.5f),
                    blendMode = BlendMode.Darken
                ),
                contentScale = ContentScale.Crop
            )
            Icon(
                painter = painterResource(R.drawable.edit_24dp_e8eaed_fill1_wght400_grad0_opsz24),
                tint = Color.White,
                contentDescription = null,
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        GeneralInfo(personalInfo = profileViewModel.personalInfo)
        Spacer(modifier = Modifier.height(24.dp))
        AttendanceInfo(attendances = profileViewModel.attendances)
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFBD3E3E),
                contentColor = Color.White
            ),
            onClick = {
                profileViewModel.showConfirmDialog = true
            },
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(2.dp, Color.Black.copy(alpha = 0.1f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Log out",
                fontWeight = FontWeight.Bold,
                fontFamily = proximaNovaFamily
            )
        }
    }
}

@Composable
fun GeneralInfo(
    personalInfo: GetPersonalInfoResponse?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.height(IntrinsicSize.Min),
    ) {
        Text(
            text = "General",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(cardBackgroundColor)
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 16.dp)
        ) {
            InfoRow(
                icon = R.drawable.badge_24dp_e8eaed_fill1_wght400_grad0_opsz24,
                label = "Name",
                value = {
                    Text(
                        text = personalInfo!!.fullName,
                        fontSize = 16.sp,
                        modifier = Modifier.alpha(0.67f)
                    )
                }
            )
            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 6.dp),
                color = Color.White.copy(0.1f)
            )
            InfoRow(
                icon = R.drawable.mail_24dp_e8eaed_fill1_wght400_grad0_opsz24,
                label = "Email",
                value = {
                    Text(
                        text = personalInfo!!.email,
                        fontSize = 16.sp,
                        modifier = Modifier.alpha(0.67f)
                    )
                }
            )
            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 6.dp),
                color = Color.White.copy(0.1f)
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                InfoRow(
                    icon = R.drawable.home_storage_24dp_e8eaed_fill1_wght400_grad0_opsz24,
                    label = "Locker",
                    value = {
                        Text(
                            text = personalInfo!!.lockerNumber,
                            fontSize = 16.sp,
                            modifier = Modifier.alpha(0.67f)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                VerticalDivider(
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 6.dp),
                    color = Color.White.copy(0.1f)
                )
                InfoRow(
                    icon = R.drawable.family_restroom_24dp_e8eaed_fill1_wght400_grad0_opsz24,
                    label = "Family",
                    value = {
                        Text(
                            text = personalInfo!!.familyNumber,
                            fontSize = 16.sp,
                            modifier = Modifier.alpha(0.67f)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun AttendanceInfo(
    attendances: GetAttendancesResponse?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Attendances",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(cardBackgroundColor)
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 16.dp)
        ) {
            InfoRow(
                icon = R.drawable.verified_24dp_e8eaed_fill1_wght400_grad0_opsz24,
                label = "Presents",
                value = {
                    Text(
                        text = attendances!!.presents,
                        fontSize = 16.sp,
                        modifier = Modifier.alpha(0.67f)
                    )
                }
            )
            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 6.dp),
                color = Color.White.copy(0.1f)
            )
            InfoRow(
                icon = R.drawable.hourglass_bottom_24dp_e8eaed_fill1_wght400_grad0_opsz24,
                label = "Lates",
                value = {
                    Text(
                        text = attendances!!.lates,
                        fontSize = 16.sp,
                        modifier = Modifier.alpha(0.67f)
                    )
                }
            )
            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 6.dp),
                color = Color.White.copy(0.1f)
            )
            InfoRow(
                icon = R.drawable.sick_24dp_e8eaed_fill1_wght400_grad0_opsz24,
                label = "Absences",
                value = {
                    Text(
                        text = attendances!!.absences,
                        fontSize = 16.sp,
                        modifier = Modifier.alpha(0.67f)
                    )
                }
            )
            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 6.dp),
                color = Color.White.copy(0.1f)
            )
            InfoRow(
                icon = R.drawable.psychology_alt_24dp_e8eaed_fill1_wght400_grad0_opsz24,
                label = "Not Taken",
                value = {
                    Text(
                        text = attendances!!.nottakens,
                        fontSize = 16.sp,
                        modifier = Modifier.alpha(0.67f)
                    )
                }
            )
        }
    }
}

@Composable
fun InfoRow(
    icon: Int,
    label: String,
    value: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null
        )
        Spacer(Modifier.width(12.dp))
        Column {
            Text(
                text = label,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            value()
        }
    }
}

@Composable
fun ChangeProfilePictureDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit,
    textValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(cardBackgroundColor)
                .border(2.dp, Color.Black.copy(alpha = 0.10f), RoundedCornerShape(12.dp))
                .padding(horizontal = 18.dp, vertical = 16.dp)
        ) {
            TextField(
                value = textValue,
                onValueChange = { onValueChange(it) },
                singleLine = true
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Paste a URL.",
                    fontFamily = proximaNovaFamily,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.alpha(0.67f)
                )
                Row {
                    TextButton(
                        onClick = onDismissRequest
                    ) {
                        Text(
                            text = "Cancel",
                            fontFamily = proximaNovaFamily,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    TextButton(
                        onClick = { onConfirmation(textValue) }
                    ) {
                        Text(
                            text = "Save",
                            fontFamily = proximaNovaFamily,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopAppBar() {
    TopAppBar(
        title = {
            Text(
                text = "Profile",
                fontFamily = proximaNovaFamily,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    )
}
