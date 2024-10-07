package com.example.almate.features.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.almate.R
import com.example.almate.data.model.GetGpaResponse
import com.example.almate.data.model.SupabaseUser
import com.example.almate.domain.model.Credentials
import com.example.almate.features.home.data.model.GetGradesResponseItem
import com.example.almate.features.home.presentation.HomeSkeletonScreen
import com.example.almate.features.home.presentation.Subject
import com.example.almate.presentation.ErrorScreen
import com.example.almate.presentation.theme.cardBackgroundColor
import kotlinx.coroutines.flow.first

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
    onSubjectClick: (Subject) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        if (homeViewModel.fetchCredentials() != homeViewModel.creds) {
            homeViewModel.creds = homeViewModel.fetchCredentials()
            homeViewModel.fetchData()
        }
    }
    when (homeViewModel.homeState) {
        is HomeState.Loading -> HomeSkeletonScreen(modifier = modifier)
        is HomeState.Success ->
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
                modifier = modifier
                    .fillMaxSize()
            ) {
                item {
                    UserInfo(homeViewModel.supabaseUser)
                }
                item {
                    Spacer(Modifier.height(24.dp))
                }
                item {
                    GpaAnalytics(homeViewModel.gpaResponse)
                }
                item {
                    Spacer(Modifier.height(24.dp))
                }
                Grades(
                    grades = homeViewModel.grades,
                    onSwitchSort = { homeViewModel.switchSort() },
                    onSubjectClick = { onSubjectClick(it) },
                    sortedAlphabetically = homeViewModel.sortedAlphabetically
                )
            }
        is HomeState.Error -> ErrorScreen(onClick = { homeViewModel.fetchData() })
    }
}

@Composable
fun UserInfo(
    supabaseUser: SupabaseUser,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(supabaseUser.profilePicture)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(82.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = supabaseUser.fullName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${supabaseUser.rankedRating}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Current ranked rating",
                fontSize = 16.sp,
                modifier = Modifier.alpha(0.67f)
            )
        }
    }
}

@Composable
fun GpaAnalytics(
    gpaResponse: GetGpaResponse,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "GPA Analytics",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(cardBackgroundColor)
                    .border(2.dp, Color.Black.copy(alpha = 0.10f), RoundedCornerShape(12.dp))
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = gpaResponse.current,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "GPA",
                    fontSize = 16.sp,
                    modifier = Modifier.alpha(0.67f)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(cardBackgroundColor)
                    .border(2.dp, Color.Black.copy(alpha = 0.10f), RoundedCornerShape(12.dp))
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = gpaResponse.cumulative,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Cum. GPA",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp,
                    modifier = Modifier.alpha(0.67f)
                )
            }
        }
    }
}

fun LazyListScope.Grades(
    grades: List<GetGradesResponseItem>,
    onSwitchSort: () -> Unit,
    sortedAlphabetically: Boolean,
    onSubjectClick: (Subject) -> Unit,
    modifier: Modifier = Modifier
) {
    item {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Grades",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onSwitchSort() }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .alpha(0.67f)
                ) {
                    Text(
                        text = if (sortedAlphabetically) "Alphabet" else "Percentage",
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.sort_16dp_e8eaed_fill1_wght400_grad0_opsz20),
                        contentDescription = null
                    )
                }
            }
        }
    }
    item {
        Spacer(modifier = Modifier.height(8.dp))
    }
    items(grades) { grade ->
        GradeCard(
            gradesResponseItem = grade,
            onSubjectClick = { onSubjectClick(it) }
        )
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
fun GradeCard(
    gradesResponseItem: GetGradesResponseItem,
    onSubjectClick: (Subject) -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (gradesResponseItem.letter) {
        "A+" -> Color(0xFF85C658)
        "A" -> Color(0xFF6E9F50)
        "A-" -> Color(0xFF55773C)
        // old colors
//        "A" -> Color(0xFF8AB46E)
//        "A-" -> Color(0xFF517939)
        "B+" -> Color(0xFFF8AA52)
        "B" -> Color(0xFFAB5F07)
        "B-" -> Color(0xFF5B3206)
        "C+" -> Color(0xFFEB6841)
        "C" -> Color(0xFFC23D14)
        "D+" -> Color(0xFF803C27)
        "D" -> Color(0xFF552D21)
        else -> cardBackgroundColor
    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onSubjectClick(Subject(path = gradesResponseItem.path)) }
            .background(color = backgroundColor)
            .border(2.dp, Color.Black.copy(alpha = 0.10f), RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier.weight(0.7f)
        ) {
            if (gradesResponseItem.percent != "-") {
                Text(
                    text = gradesResponseItem.percent,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            Text(
                text = gradesResponseItem.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = gradesResponseItem.teacher,
                fontSize = 14.sp
            )
        }
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .weight(0.3f)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .size(36.dp)
            ) {
                Text(
                    text =  gradesResponseItem.letter,
                    fontSize = 14.sp,
                    color = backgroundColor,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
//                    .border(2.dp, Color.White.copy(0.67f), RoundedCornerShape(10.dp))
                    .background(Color.White.copy(0.33f))
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.weight_24dp_e8eaed_fill1_wght400_grad0_opsz24),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = gradesResponseItem.weight,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
        }
    }
}
