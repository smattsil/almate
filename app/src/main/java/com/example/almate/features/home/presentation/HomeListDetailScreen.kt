package com.example.almate.features.home.presentation

import android.os.Parcelable
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.almate.R
import com.example.almate.features.home.data.model.GetSubjectResponse
import com.example.almate.features.home.presentation.components.HomeScreen
import com.example.almate.features.home.presentation.components.HomeViewModel
import com.example.almate.features.home.presentation.components.SubjectScreen
import com.example.almate.features.home.presentation.components.SubjectViewModel
import com.example.almate.presentation.theme.proximaNovaFamily
import kotlinx.parcelize.Parcelize

@Parcelize
class Subject(val path: String) : Parcelable

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun HomeListDetailScreen(
    homeViewModel: HomeViewModel,
    subjectViewModel: SubjectViewModel,
    onProfilePictureClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    val subjectOpened = navigator.currentDestination?.pane == ListDetailPaneScaffoldRole.Detail

    Scaffold(
        topBar = {
            if (!subjectOpened) HomeTopAppBar(
                profilePictureUrl = homeViewModel.supabaseUser.profilePicture,
                onProfilePictureClick = onProfilePictureClick
            ) else SubjectTopAppBar(
                subjectResponse = subjectViewModel.subjectResponse,
                navigateBack = {
                    navigator.navigateBack()
                    subjectViewModel.clearSubjectResponse()
                }
            )
        }
    ) { innerPadding ->
        NavigableListDetailPaneScaffold(
            navigator = navigator,
            listPane = {
                AnimatedPane {
                    HomeScreen(
                        onSubjectClick = { subject ->
                            navigator.navigateTo(
                                pane = ListDetailPaneScaffoldRole.Detail,
                                content = subject
                            )
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            },
            detailPane = {
                AnimatedPane {
                    if (subjectOpened) {
                        navigator.currentDestination?.content?.let { subject ->
                            SubjectScreen(
                                subject = subject as Subject,
                                subjectViewModel = subjectViewModel,
                                navigateBack = { navigator.navigateBack() },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    } else {
                        SubjectPlaceholderScreen()
                    }
                }
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    onProfilePictureClick: () -> Unit,
    profilePictureUrl: String
) {
    TopAppBar(
        title = {
            Icon(
                painter = painterResource(R.drawable.almate),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.notifications_24dp_e8eaed_fill0_wght400_grad0_opsz24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                )
            }
            IconButton(onClick = onProfilePictureClick) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(profilePictureUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                )
            }
        },
        modifier = Modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectTopAppBar(
    subjectResponse: GetSubjectResponse,
    navigateBack: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back_24dp_e8eaed_fill0_wght400_grad0_opsz24),
                    contentDescription = null
                )
            }
        },
        title = {
            AnimatedContent(subjectResponse) {
                Column {
                    Text(
                        text = it.name,
                        fontFamily = proximaNovaFamily,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = it.teacher,
                        fontFamily = proximaNovaFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        lineHeight = MaterialTheme.typography.labelSmall.lineHeight,
                        color = Color.White.copy(0.67f)
                    )
                }
            }
        }
    )
}
