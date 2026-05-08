package com.waarstreamt.het.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.waarstreamt.het.ui.theme.Background
import com.waarstreamt.het.ui.theme.Muted
import com.waarstreamt.het.ui.theme.Teal

@Composable
fun SavedScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(280.dp)
                .background(
                    Brush.radialGradient(listOf(Teal.copy(alpha = 0.06f), Color.Transparent)),
                ),
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "WAAR\nSTREAMT\n.HET",
                style = MaterialTheme.typography.displayLarge.copy(lineHeight = 64.sp),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Opgeslagen komt binnenkort",
                style = MaterialTheme.typography.bodyMedium,
                color = Muted,
            )
        }
    }
}
