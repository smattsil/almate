package com.example.almate.features.rankings.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.almate.R
import com.example.almate.data.model.SupabaseUser
import com.example.almate.presentation.ErrorScreen
import com.example.almate.presentation.SmallLoadingScreen
import com.example.almate.presentation.theme.cardBackgroundColor
import com.example.almate.presentation.theme.proximaNovaFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingsScreen(
    rankingsViewModel: RankingsViewModel,
    scrollState: ScrollState = rememberScrollState(),
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = "Rankings",
                            fontFamily = proximaNovaFamily,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    modifier = Modifier
                )
                var periodIndex by remember { mutableIntStateOf(0) }
                TabRow(selectedTabIndex = periodIndex) {
                    Tab(
                        selected = periodIndex == 0,
                        onClick = { periodIndex = 0 },
                        text = {
                            Text(
                                text = "Everyone",
                                fontFamily = proximaNovaFamily,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    )
                    Tab(
                        selected = periodIndex == 1,
                        onClick = { periodIndex = 1 },
                        text = {
                            Text(
                                text = "Friends",
                                fontFamily = proximaNovaFamily,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        when (rankingsViewModel.leaderboardState) {
            is LeaderboardState.Loading -> RankingsSkeletonScreen(modifier = modifier.padding(innerPadding))
            is LeaderboardState.Success ->
                Column(
                    modifier = modifier
                        .verticalScroll(scrollState)
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                        .animateContentSize()
                        .padding(innerPadding)
                ) {
                    SearchBlock(
                        onClick = { /*TODO: Add search handle.*/ }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    rankingsViewModel.supabaseUsers.forEachIndexed { index, user ->
                        User(index + 1, user)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

            is LeaderboardState.Error -> ErrorScreen(onClick = { rankingsViewModel.fetchUsers() })
        }
    }
}

@Composable
fun SearchBlock(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .background(cardBackgroundColor)
            .border(2.dp, Color.Black.copy(alpha = 0.10f), RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 16.dp)
            .alpha(0.67f)
    ) {
        Icon(
            painter = painterResource(R.drawable.search_24dp_e8eaed_fill1_wght400_grad0_opsz24),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Search users",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun User(
    index: Int,
    supabaseUser: SupabaseUser,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable {  }
            .background(cardBackgroundColor)
            .border(2.dp, Color.Black.copy(alpha = 0.10f), RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(24.dp)
            ) {
                Text(
                    text = "$index",
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(supabaseUser.profilePicture)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = formatName(supabaseUser.fullName),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.alpha(0.67f)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.update_24dp_e8eaed_fill1_wght400_grad0_opsz24),
                        contentDescription = null,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = supabaseUser.lastUpdated,
                        fontSize = 12.sp
                    )
                }
            }
        }
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "${supabaseUser.rankedRating}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${supabaseUser.gpa} GPA",
                fontSize = 16.sp,
                modifier = Modifier.alpha(0.67f)
            )
        }
    }
}

fun formatName(fullName: String): String {
    val nameParts = fullName.split(" ")
    if (nameParts.isEmpty()) return ""

    val firstName = nameParts[0]
    val initials = nameParts.drop(1).map { it.first().uppercaseChar() + "." }

    return (listOf(firstName) + initials).joinToString(" ")
}
