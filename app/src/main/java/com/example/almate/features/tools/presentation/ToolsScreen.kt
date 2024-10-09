package com.example.almate.features.tools.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.almate.R
import com.example.almate.features.tools.presentation.chat.ChatScreen
import com.example.almate.presentation.theme.cardBackgroundColor
import com.example.almate.presentation.theme.proximaNovaFamily
import kotlinx.serialization.Serializable

@Serializable data object ToolsGrid
@Serializable data object Chat
@Serializable data object GradePredictor
@Serializable data object FinalGradeCalculator
@Serializable data object StudyTips
@Serializable data object GoalSetter
@Serializable data object University
@Serializable data object JobProspects
@Serializable data object Celebrities

enum class Tools(
    val icon: Int,
    val label: String,
    val route: Any,
    val enabled: Boolean,
    val description: String
) {
    CHAT(
        icon = R.drawable.robot_24dp_e8eaed_fill0_wght400_grad0_opsz48,
        label = "Chat",
        route = Chat,
        enabled = true,
        description = "Talk to Almate (AI) for insights on your grades and GPA."
    ),
    FINAL_GRADE_CALCULATOR(
        icon = R.drawable.function_24dp_e8eaed_fill0_wght400_grad0_opsz48,
        label = "Final Grade Calculator",
        route = FinalGradeCalculator,
        enabled = true,
        description = "Calculate how much you need on your final for a certain grade."
    ),
    GRADE_PREDICTOR(
        icon = R.drawable.book_4_spark_24dp_e8eaed_fill0_wght400_grad0_opsz48,
        label = "Grade Predictor",
        route = GradePredictor,
        enabled = false,
        description = "Use AI to predict where your grades are heading."
    ),
    STUDY_TIPS(
        icon = R.drawable.edit_24dp_e8eaed_fill0_wght400_grad0_opsz48,
        label = "Study Tips",
        route = StudyTips,
        enabled = false,
        description = "View personalized study tips based on your performance."
    ),
    GOAL_SETTER(
        icon = R.drawable.flag_24dp_e8eaed_fill0_wght400_grad0_opsz48,
        label = "Goal Setter",
        route = GoalSetter,
        enabled = false,
        description = "Set goals for subjects and track your progress."
    ),
    UNIVERSITY(
        icon = R.drawable.school_24dp_e8eaed_fill0_wght400_grad0_opsz48,
        label = "University",
        route = University,
        enabled = false,
        description = "Get university recommendations based on your capabilities."
    ),
    JOB_PROSPECTS(
        icon = R.drawable.hail_24dp_e8eaed_fill0_wght400_grad0_opsz48,
        label = "Job Prospects",
        route = JobProspects,
        enabled = false,
        description = "See how your strengths could shape your career."
    ),
    CELEBRITIES(
    icon = R.drawable.taunt_24dp_e8eaed_fill0_wght400_grad0_opsz48,
    label = "Celebrities",
    route = Celebrities,
    enabled = false,
    description = "Find out which celebrities had the same grades as you!"
    )
}

@Preview(showBackground = true)
@Composable
fun ToolsScreen(
    modifier: Modifier = Modifier
) {
    val toolsNavController = rememberNavController()
    NavHost(
        navController = toolsNavController,
        startDestination = ToolsGrid,
        modifier = modifier
    ) {
        composable<ToolsGrid> {
            ToolsGrid(onToolClick = { toolsNavController.navigate(it) })
        }
        composable<Chat> {
            ChatScreen(
                onBackButtonClick = { toolsNavController.navigateUp() }
            )
        }
        composable<GradePredictor> {
        }
        composable<FinalGradeCalculator> {
        }
        composable<StudyTips> {
        }
        composable<GoalSetter> {
        }
        composable<JobProspects> {
        }
    }
}

@Composable
fun ToolsGrid(
    onToolClick: (Any) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            ToolsTopAppBar()
        }
    ) { innerPadding ->
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(count = 2),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
            modifier = modifier.padding(innerPadding)
        ) {
            items(Tools.entries) { tool ->
                Tool(
                    enabled = tool.enabled,
                    icon = tool.icon,
                    label = tool.label,
                    description = tool.description,
                    onToolClick = { onToolClick(tool.route) }
                )
            }
        }
    }
}

@Composable
fun Tool(
    enabled: Boolean,
    icon: Int,
    label: String,
    description: String,
    onToolClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .alpha(if (enabled) 1f else 0.7f)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .then(
                if (enabled) {
                    Modifier.clickable { onToolClick() }
                } else {
                    Modifier
                }
            )
            .background(cardBackgroundColor)
            .border(2.dp, Color.Black.copy(0.1f), RoundedCornerShape(12.dp))
            .padding(horizontal = 18.dp, vertical = 16.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = label,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = description,
            fontSize = 16.sp,
            modifier = Modifier.alpha(0.67f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolsTopAppBar() {
    TopAppBar(
        title = {
            Text(
                text = "Tools",
                fontFamily = proximaNovaFamily,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    )
}
