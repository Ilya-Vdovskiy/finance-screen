package com.example.ui_with_jetpack_compose.ui.finance

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FinanceApp(widthSizeClass: WindowWidthSizeClass) {
    var selectedTransactionId by rememberSaveable { mutableStateOf<String?>(null) }
    val selectedTransaction = FinanceSamples.content.transactions.firstOrNull { it.id == selectedTransactionId }

    BackHandler(enabled = selectedTransaction != null) { selectedTransactionId = null }

    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        if (selectedTransaction == null) {
            OverviewScreen(
                state = OverviewState.Ready(FinanceSamples.content),
                onTransactionClick = { selectedTransactionId = it.id },
                onFill = {},
                wide = widthSizeClass != WindowWidthSizeClass.Compact,
            )
        } else {
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
