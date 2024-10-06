package com.example.almate.features.profile.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.almate.R
import com.example.almate.presentation.theme.cardBackgroundColor
import com.example.almate.presentation.theme.proximaNovaFamily

@Preview(showBackground = true)
@Composable
fun LogOutDialog(
    onButtonClick: (Boolean) -> Unit = {},
    onDismissRequest: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var checked: Boolean by remember { mutableStateOf(false) }
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .clip(RoundedCornerShape(12.dp))
                .background(cardBackgroundColor)
                .padding(horizontal = 18.dp, vertical = 16.dp)
        ) {
            Icon(
                painterResource(R.drawable.almate),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(144.dp)
            )
            Text(
                text = "Log out",
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                fontWeight = FontWeight.Bold,
                fontFamily = proximaNovaFamily
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Are you sure you want to log out? By default, your data won't be deleted.",
                textAlign = TextAlign.Center,
                fontFamily = proximaNovaFamily
            )
            Spacer(Modifier.height(12.dp))
            Button(
                onClick = { onButtonClick(checked) },
                border = BorderStroke(2.dp, Color.Black.copy(0.1f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(R.drawable.logout_24dp_e8eaed_fill0_wght400_grad0_opsz24),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "Log out",
                        fontFamily = proximaNovaFamily
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { checked = !checked }
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { checked = !checked }
                )
                Text(
                    text = "Remove my data",
                    fontFamily = proximaNovaFamily
                )
            }

        }
    }
}