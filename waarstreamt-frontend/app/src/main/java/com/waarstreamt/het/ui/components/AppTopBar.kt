package com.waarstreamt.het.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.waarstreamt.het.ui.theme.Background
import com.waarstreamt.het.ui.theme.BebasNeue
import com.waarstreamt.het.ui.theme.Muted

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    onLogoClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Text(
                text = "WaarStreamt.Het",
                style = TextStyle(fontFamily = BebasNeue, fontSize = 26.sp, letterSpacing = 0.5.sp),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.clickable(onClick = onLogoClick),
            )
        },
        actions = {
            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = "Instellingen",
                    tint = Muted,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Background),
        modifier = modifier,
    )
}
