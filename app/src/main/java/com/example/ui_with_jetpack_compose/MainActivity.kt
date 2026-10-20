package com.example.ui_with_jetpack_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.example.ui_with_jetpack_compose.ui.finance.FinanceApp
import com.example.ui_with_jetpack_compose.ui.theme.FinanceTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            FinanceTheme {
                FinanceApp(windowSizeClass.widthSizeClass)
            }
        }
    }
}
