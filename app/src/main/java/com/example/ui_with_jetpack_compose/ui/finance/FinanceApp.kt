package com.example.ui_with_jetpack_compose.ui.finance

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.view.WindowInsetsControllerCompat
import com.example.ui_with_jetpack_compose.ui.theme.FinanceTheme

@Composable
fun FinanceApp(widthSizeClass: WindowWidthSizeClass) {
    val context = LocalContext.current
    val preferences = remember(context) { context.getSharedPreferences("finance_demo", Context.MODE_PRIVATE) }
    var themeMode by rememberSaveable { mutableIntStateOf(preferences.getInt("theme_mode", 0)) }
    var demoMode by rememberSaveable { mutableIntStateOf(0) }
    var selectedTransactionId by rememberSaveable { mutableStateOf<String?>(null) }
    val darkTheme = when (themeMode) {
        1 -> false
        2 -> true
        else -> isSystemInDarkTheme()
    }

    BackHandler(enabled = selectedTransactionId != null) { selectedTransactionId = null }

    FinanceTheme(darkTheme = darkTheme) {
        val background = MaterialTheme.colorScheme.background
        SideEffect {
            (context as? Activity)?.window?.let { window ->
                window.statusBarColor = background.toArgb()
                window.navigationBarColor = background.toArgb()
                WindowInsetsControllerCompat(window, window.decorView).apply {
                    isAppearanceLightStatusBars = !darkTheme
                    isAppearanceLightNavigationBars = !darkTheme
                }
            }
        }
        Surface(color = background, modifier = Modifier.fillMaxSize()) {
            AnimatedContent(
                targetState = selectedTransactionId to demoMode,
                transitionSpec = { fadeIn(tween(220)) togetherWith fadeOut(tween(150)) },
                label = "Переход между экранами",
            ) { (transactionId, stateMode) ->
                if (transactionId == null) {
                    val state = when (stateMode) {
                        1 -> OverviewState.Loading
                        2 -> OverviewState.Empty
                        else -> OverviewState.Ready(FinanceSamples.content)
                    }
                    OverviewScreen(
                        state = state,
                        onTransactionClick = { selectedTransactionId = it.id },
                        onFill = { demoMode = 0 },
                        wide = widthSizeClass != WindowWidthSizeClass.Compact,
                        themeMode = themeMode,
                        onThemeModeChange = { mode ->
                            themeMode = mode
                            preferences.edit().putInt("theme_mode", mode).apply()
                        },
                        demoMode = demoMode,
                        onDemoModeChange = { demoMode = it },
                    )
                } else {
                    val selectedTransaction = FinanceSamples.content.transactions.firstOrNull { it.id == transactionId }
                    if (selectedTransaction != null) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                            TransactionDetailScreen(
                                transaction = selectedTransaction,
                                onBack = { selectedTransactionId = null },
                                modifier = Modifier.widthIn(max = 680.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}
