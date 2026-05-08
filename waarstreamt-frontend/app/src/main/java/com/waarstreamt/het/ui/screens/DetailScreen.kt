package com.waarstreamt.het.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.waarstreamt.het.data.model.ContentDetail
import com.waarstreamt.het.data.model.ContentType
import com.waarstreamt.het.data.model.StreamingService
import com.waarstreamt.het.ui.theme.Background
import com.waarstreamt.het.ui.theme.Muted
import com.waarstreamt.het.ui.theme.Surface
import com.waarstreamt.het.ui.theme.SurfaceVariant
import com.waarstreamt.het.ui.theme.Teal
import com.waarstreamt.het.ui.viewmodel.SearchViewModel

@Composable
fun DetailScreen(
    contentId: String,
    onBack: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val state by viewModel.detailUiState.collectAsState()

    LaunchedEffect(contentId) {
        viewModel.loadDetail(contentId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {
        AnimatedContent(targetState = state, label = "detail") { s ->
            when {
                s.isLoading -> LoadingState()
                s.error != null -> ErrorState(s.error)
                s.detail != null -> DetailContent(s.detail, onBack)
            }
        }
    }
}

@Composable
private fun DetailContent(detail: ContentDetail, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        // ── Hero ──────────────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(340.dp),
        ) {
            if (detail.backdropUrl != null || detail.posterUrl != null) {
                AsyncImage(
                    model = detail.backdropUrl ?: detail.posterUrl,
                    contentDescription = detail.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
            } else {
                // Placeholder with gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    SurfaceVariant,
                                    Surface,
                                ),
                            ),
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = if (detail.type == ContentType.MOVIE) "🎬" else "📺",
                        fontSize = 72.sp,
                    )
                }
            }

            // Gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colorStops = arrayOf(
                                0.3f to Color.Transparent,
                                1f to Background,
                            ),
                        ),
                    ),
            )

            // Back button
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(8.dp)
                    .size(40.dp)
                    .background(Background.copy(alpha = 0.7f), CircleShape),
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Terug",
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }

            // Title at bottom of hero
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 20.dp, vertical = 16.dp),
            ) {
                Text(
                    text = detail.title,
                    style = MaterialTheme.typography.displaySmall,
                    color = Color.White,
                )
                detail.year?.let {
                    Text(
                        text = buildString {
                            append(it)
                            append(" · ")
                            append(if (detail.type == ContentType.MOVIE) "Film" else "Serie")
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.6f),
                    )
                }
            }
        }

        // ── Body ─────────────────────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        ) {
            // Streaming services
            SectionLabel("Beschikbaar op")
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                detail.streamingServices.forEach { service ->
                    ServiceBadge(service)
                }
            }

            Divider()

            // Summary
            SectionLabel("Over dit verhaal")
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = detail.summary,
                style = MaterialTheme.typography.bodyMedium,
                color = Muted,
                lineHeight = 22.sp,
            )

            Divider()

            // Ratings
            SectionLabel("Beoordelingen")
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                detail.imdbRating?.let { rating ->
                    RatingCard(
                        modifier = Modifier.weight(1f),
                        emoji = "⭐",
                        score = "${rating}/10",
                        label = "IMDb score",
                        scoreColor = Color(0xFFF5C518),
                    )
                }
                detail.rtScore?.let { score ->
                    RatingCard(
                        modifier = Modifier.weight(1f),
                        emoji = "🍅",
                        score = "$score%",
                        label = "Rotten Tomatoes",
                        scoreColor = Color(0xFFFA320A),
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.labelSmall,
        color = Teal,
        letterSpacing = 2.sp,
    )
}

@Composable
private fun Divider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
            .height(1.dp)
            .background(Color.White.copy(alpha = 0.06f)),
    )
}

@Composable
private fun ServiceBadge(service: StreamingService) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White.copy(alpha = 0.06f))
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.1f),
                shape = RoundedCornerShape(10.dp),
            )
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(
                    color = parseColor(service.brandColor),
                    shape = CircleShape,
                ),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = service.name,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun RatingCard(
    modifier: Modifier = Modifier,
    emoji: String,
    score: String,
    label: String,
    scoreColor: Color,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.04f))
            .border(1.dp, Color.White.copy(alpha = 0.07f), RoundedCornerShape(12.dp))
            .padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = emoji, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = score,
            style = MaterialTheme.typography.displaySmall,
            color = scoreColor,
            fontSize = 28.sp,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Muted,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = Teal)
    }
}

@Composable
private fun ErrorState(error: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = error,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(24.dp),
        )
    }
}

private fun parseColor(hex: String): Color {
    return try {
        Color(android.graphics.Color.parseColor(hex))
    } catch (e: Exception) {
        Color.Gray
    }
}
