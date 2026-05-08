package com.waarstreamt.het.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.waarstreamt.het.ui.theme.Background
import com.waarstreamt.het.ui.theme.BebasNeue
import com.waarstreamt.het.ui.theme.Muted
import com.waarstreamt.het.ui.theme.OnSurface
import com.waarstreamt.het.ui.theme.SurfaceVariant
import com.waarstreamt.het.ui.theme.Teal
import com.waarstreamt.het.ui.viewmodel.SettingsViewModel

private val LANGUAGES = listOf("Nederlands", "English", "Français", "Deutsch", "Español")
private val COUNTRIES = listOf("Nederland", "België", "United Kingdom", "United States", "Deutschland", "France")
private val STREAMING_SERVICES = listOf(
    "netflix" to "Netflix",
    "disney" to "Disney+",
    "prime" to "Prime Video",
    "max" to "Max",
    "apple" to "Apple TV+",
    "streamz" to "Streamz",
    "vtmgo" to "VTM GO",
    "mubi" to "MUBI",
    "peacock" to "Peacock",
    "paramount" to "Paramount+",
    "skyshowtime" to "SkyShowtime",
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onNavigateHome: () -> Unit,
    onBack: () -> Unit,
) {
    val language by viewModel.language.collectAsState()
    val country by viewModel.country.collectAsState()
    val ownedServices by viewModel.ownedServices.collectAsState()

    Scaffold(
        containerColor = Background,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "Terug",
                            tint = Muted,
                        )
                    }
                },
                title = {
                    Text(
                        text = "WaarStreamt.Het",
                        style = TextStyle(fontFamily = BebasNeue, fontSize = 26.sp, letterSpacing = 0.5.sp),
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.clickable(onClick = onNavigateHome),
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Background),
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
        ) {
            item {
                Spacer(Modifier.height(16.dp))
                SectionHeader("Taal & Regio")
                Spacer(Modifier.height(16.dp))
                DropdownSetting(
                    label = "Taal",
                    selected = language,
                    options = LANGUAGES,
                    onSelect = viewModel::setLanguage,
                )
                Spacer(Modifier.height(16.dp))
                DropdownSetting(
                    label = "Land",
                    selected = country,
                    options = COUNTRIES,
                    onSelect = viewModel::setCountry,
                )
                Spacer(Modifier.height(8.dp))
            }

            item {
                SettingsDivider()
                SectionHeader("Mijn Streamingdiensten")
                Spacer(Modifier.height(4.dp))
            }

            items(STREAMING_SERVICES) { (id, name) ->
                ServiceRow(
                    name = name,
                    isOwned = id in ownedServices,
                    onToggle = { viewModel.toggleService(id) },
                )
            }

            item { Spacer(Modifier.height(40.dp)) }
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.labelSmall,
        color = Teal,
        letterSpacing = 2.sp,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownSetting(
    label: String,
    selected: String,
    options: List<String>,
    onSelect: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Muted,
            modifier = Modifier.padding(bottom = 6.dp),
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            OutlinedTextField(
                value = selected,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Teal.copy(alpha = 0.6f),
                    unfocusedBorderColor = Teal.copy(alpha = 0.2f),
                    focusedContainerColor = SurfaceVariant,
                    unfocusedContainerColor = SurfaceVariant.copy(alpha = 0.5f),
                    focusedTextColor = OnSurface,
                    unfocusedTextColor = OnSurface,
                    focusedTrailingIconColor = Muted,
                    unfocusedTrailingIconColor = Muted,
                ),
                textStyle = MaterialTheme.typography.bodyMedium,
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(SurfaceVariant),
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option,
                                style = MaterialTheme.typography.bodyMedium,
                                color = OnSurface,
                            )
                        },
                        onClick = { onSelect(option); expanded = false },
                        modifier = Modifier.background(SurfaceVariant),
                    )
                }
            }
        }
    }
}

@Composable
private fun ServiceRow(
    name: String,
    isOwned: Boolean,
    onToggle: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            color = OnSurface,
            modifier = Modifier.weight(1f),
        )
        Switch(
            checked = isOwned,
            onCheckedChange = { onToggle() },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Background,
                checkedTrackColor = Teal,
                uncheckedThumbColor = Muted,
                uncheckedBorderColor = Muted.copy(alpha = 0.4f),
                uncheckedTrackColor = SurfaceVariant,
            ),
        )
    }
}

@Composable
private fun SettingsDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
            .height(1.dp)
            .background(OnSurface.copy(alpha = 0.06f)),
    )
}
