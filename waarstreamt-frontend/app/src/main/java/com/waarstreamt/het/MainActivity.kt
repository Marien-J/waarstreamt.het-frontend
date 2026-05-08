package com.waarstreamt.het

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.waarstreamt.het.ui.WaarStreamtNavGraph
import com.waarstreamt.het.ui.theme.WaarStreamtTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaarStreamtTheme {
                WaarStreamtNavGraph()
            }
        }
    }
}
