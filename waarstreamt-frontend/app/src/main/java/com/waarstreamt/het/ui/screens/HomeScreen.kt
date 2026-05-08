package com.waarstreamt.het.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.waarstreamt.het.data.model.ContentType
import com.waarstreamt.het.data.model.SearchResult
import com.waarstreamt.het.ui.theme.Muted
import com.waarstreamt.het.ui.theme.Surface
import com.waarstreamt.het.ui.theme.SurfaceVariant
import com.waarstreamt.het.ui.theme.Teal
import com.waarstreamt.het.ui.viewmodel.SearchViewModel

@Composable
fun HomeScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onResultClick: (String) -> Unit,
) {
    val state by viewModel.searchUiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        // Ambient glow behind logo
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 80.dp)
                .size(320.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Teal.copy(alpha = 0.07f),
                            Color.Transparent,
                        ),
                    ),
                ),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // ── Logo ─────────────────────────────────────────────────────────
            Text(
                text = "ONTDEK WAAR JIJ KIJKT",
                style = MaterialTheme.typography.labelSmall,
                color = Teal,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "WAAR\nSTREAMT\n.HET",
                style = MaterialTheme.typography.displayLarge.copy(
                    lineHeight = 64.sp,
                ),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Vind iedere film & serie in één klik",
                style = MaterialTheme.typography.bodySmall,
                color = Muted,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(48.dp))

            // ── Search field ─────────────────────────────────────────────────
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = state.query,
                    onValueChange = viewModel::onQueryChange,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = "Zoek een film of serie…",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Muted,
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = null,
                            tint = Teal,
                        )
                    },
                    trailingIcon = {
                        if (state.isSearching) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp,
                                color = Teal,
                            )
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Teal.copy(alpha = 0.6f),
                        unfocusedBorderColor = Teal.copy(alpha = 0.2f),
                        focusedContainerColor = SurfaceVariant,
                        unfocusedContainerColor = SurfaceVariant.copy(alpha = 0.5f),
                        cursorColor = Teal,
                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    ),
                    textStyle = MaterialTheme.typography.bodyLarge,
                )

                // ── Autocomplete dropdown ─────────────────────────────────────
                AnimatedVisibility(
                    visible = state.suggestions.isNotEmpty(),
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut(),
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        color = Surface,
                        tonalElevation = 8.dp,
                    ) {
                        LazyColumn {
                            items(state.suggestions, key = { it.id }) { result ->
                                SuggestionRow(
                                    result = result,
                                    onClick = { onResultClick(result.id) },
                                )
                            }
                        }
                    }
                }
            }

            // ── Error ─────────────────────────────────────────────────────────
            state.error?.let { error ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = error,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                )
            }

            // ── Hint ─────────────────────────────────────────────────────────
            if (state.query.isEmpty()) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Typ minstens 2 tekens om te zoeken",
                    style = MaterialTheme.typography.bodySmall,
                    color = Muted.copy(alpha = 0.4f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 48.dp),
                )
            }
        }
    }
}

@Composable
private fun SuggestionRow(result: SearchResult, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Placeholder thumb
        Box(
            modifier = Modifier
                .size(width = 36.dp, height = 50.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(SurfaceVariant),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = if (result.type == ContentType.MOVIE) "🎬" else "📺",
                fontSize = 18.sp,
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = result.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = buildString {
                    result.year?.let { append(it) }
                    append(" · ")
                    append(if (result.type == ContentType.MOVIE) "Film" else "Serie")
                },
                style = MaterialTheme.typography.bodySmall,
                color = Muted,
            )
        }
    }
}
