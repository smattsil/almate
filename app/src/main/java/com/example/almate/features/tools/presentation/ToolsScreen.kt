package com.example.almate.features.tools.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
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
import com.example.almate.R
import com.example.almate.presentation.theme.AlmateTheme
import com.example.almate.presentation.theme.cardBackgroundColor
import com.example.almate.presentation.theme.proximaNovaFamily

val tools = listOf(
    Tool(
        icon = R.drawable.robot_24dp_e8eaed_fill0_wght400_grad0_opsz48,
        name = "Chat",
        description = "Talk to Almate AI for insights on your grades and GPA."
    ),
    Tool(
        icon = R.drawable.book_4_spark_24dp_e8eaed_fill0_wght400_grad0_opsz48,
        name = "Grade Predictor",
        description = "Use AI to predict where your grades are heading."
    ),
    Tool(
        icon = R.drawable.function_24dp_e8eaed_fill0_wght400_grad0_opsz48,
        name = "Final Grade Calculator",
        description = "Calculate how much you need on your final for a certain grade."
    ),
    Tool(
        icon = R.drawable.school_24dp_e8eaed_fill0_wght400_grad0_opsz48,
        name = "Study Tips",
        description = "View personalized study tips based on your performance."
    ),
    Tool(
        icon = R.drawable.flag_24dp_e8eaed_fill0_wght400_grad0_opsz48,
        name = "Goal Setter",
        description = "Set goals for subjects and track your progress."
    ),
    Tool(
        icon = R.drawable.hail_24dp_e8eaed_fill0_wght400_grad0_opsz48,
        name = "Job Prospects",
        description = "See how your strengths could shape your career."
    ),
)

@Preview(showBackground = true)
@Composable
fun ToolsScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { ToolsTopAppBar() }
    ) { innerPadding ->
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(count = 2),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
            modifier = Modifier.padding(innerPadding)
        ) {
            items(tools.size) { index ->
                Tool(tool = tools[index])
            }
        }
    }
}

@Composable
fun Tool(
    tool: Tool,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { }
            .background(cardBackgroundColor)
            .border(2.dp, Color.Black.copy(0.1f), RoundedCornerShape(12.dp))
            .padding(horizontal = 18.dp, vertical = 16.dp)
    ) {
        Icon(
            painter = painterResource(tool.icon),
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = tool.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = tool.description,
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
