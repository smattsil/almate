package com.example.almate.features.home.presentation.components

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.almate.R
import com.example.almate.features.home.data.model.Assignment
import com.example.almate.features.home.data.model.GetSubjectResponse
import com.example.almate.features.home.presentation.Subject
import com.example.almate.features.home.presentation.SubjectSkeletonScreen
import com.example.almate.presentation.ErrorScreen
import com.example.almate.presentation.theme.cardBackgroundColor

@Composable
fun SubjectScreen(
    subject: Subject,
    subjectViewModel: SubjectViewModel = hiltViewModel(),
    scrollState: ScrollState = rememberScrollState(),
    modifier: Modifier = Modifier
) {
    LaunchedEffect(subject.path) {
        subjectViewModel.fetchData(subject.path)
        scrollState.scrollTo(0)
    }
    if (subjectViewModel.showSortBottomSheet) {
        SortAssignmentBottomSheet(
            onDismissRequest = { subjectViewModel.showSortBottomSheet = false },
            onSortSelection = { subjectViewModel.sortBy(it) },
            sortType = subjectViewModel.sortType
        )
    }
    when (subjectViewModel.subjectState) {
        is SubjectState.Loading -> SubjectSkeletonScreen(modifier = modifier)
        is SubjectState.Success ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                OverallGrade(subjectViewModel.subjectResponse)
                Spacer(Modifier.height(24.dp))
                Assignments(
                    assignments = subjectViewModel.subjectResponse.assignments,
                    onSortClick = { subjectViewModel.showSortBottomSheet = true },
                    sortType = subjectViewModel.sortType
                )
            }
        is SubjectState.Error -> ErrorScreen(onClick = { subjectViewModel.fetchData(subject.path) })
    }
}

@Composable
fun OverallGrade(
    subjectResponse: GetSubjectResponse,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.height(IntrinsicSize.Min)
    ) {
        Text(
            text = "Overall Grade",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(cardBackgroundColor)
                    .padding(horizontal = 18.dp, vertical = 16.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    text = subjectResponse.letter,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "(${subjectResponse.percent})",
                    fontSize = 16.sp,
                    modifier = Modifier.alpha(0.67f)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                for (category in subjectResponse.categories.sortedByDescending { it.weight.trim('%').toInt() }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(7f)
                        ) {
                            Text(
                                text = category.name,
                                fontSize = 16.sp,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${category.weight} of Final Grade",
                                fontSize = 12.sp,
                                modifier = Modifier.alpha(0.67f)
                            )
                        }
                        Text(
                            text = category.percent,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.End,
                            modifier = Modifier.weight(3f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Assignments(
    assignments: List<Assignment>,
    onSortClick: () -> Unit,
    sortType: AssignmentSortType,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Assignments",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onSortClick() }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .alpha(0.67f)
                ) {
                    Text(
                        text = sortType.toString().titleCase(),
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
        Spacer(modifier = Modifier.height(8.dp))
        for (assignment in assignments) {
            Assignment(assignment)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun Assignment(
    assignment: Assignment,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(cardBackgroundColor)
            .border(2.dp, Color.Black.copy(alpha = 0.10f), RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.weight(8f)
        ) {
            Text(
                text = assignment.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.alpha(0.67f)
            ) {
                Icon(
                    painter = painterResource(R.drawable.category_16dp_e8eaed_fill0_wght400_grad0_opsz20),
                    contentDescription = null,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = assignment.category,
                    fontSize = 12.sp
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.weight(2f)
        ) {
            Text(
                text = if (assignment.percent == "-") "" else assignment.percent,
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
                    text = assignment.date,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortAssignmentBottomSheet(
    onDismissRequest: () -> Unit,
    onSortSelection: (AssignmentSortType) -> Unit,
    sortType: AssignmentSortType,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        containerColor = cardBackgroundColor,
        modifier = modifier
    ) {
        Column {
            for (type in AssignmentSortType.entries) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .clickable {
                            onSortSelection(type)
                            onDismissRequest()
                        }
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = type.toString().titleCase()
                    )
                    RadioButton(
                        selected = type == sortType,
                        onClick = {
                            onSortSelection(type)
                            onDismissRequest()
                        }
                    )
                }
            }
        }
    }
}
