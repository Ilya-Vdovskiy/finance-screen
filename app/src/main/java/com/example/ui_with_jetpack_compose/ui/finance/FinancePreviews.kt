package com.example.ui_with_jetpack_compose.ui.finance

import android.content.res.Configuration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui_with_jetpack_compose.ui.theme.FinanceTheme

@Preview(name = "Обзор · светлая", showBackground = true, widthDp = 393, heightDp = 850)
@Composable
private fun LightOverviewPreview() {
    FinanceTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            OverviewScreen(OverviewState.Ready(FinanceSamples.content), {}, {})
        }
    }
}

@Preview(name = "Обзор · тёмная", uiMode = Configuration.UI_MODE_NIGHT_YES, widthDp = 393, heightDp = 850)
@Composable
private fun DarkOverviewPreview() {
    FinanceTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            OverviewScreen(OverviewState.Ready(FinanceSamples.content), {}, {})
        }
    }
}

@Preview(name = "Загрузка", widthDp = 393, heightDp = 850)
@Composable
private fun LoadingPreview() {
    FinanceTheme(darkTheme = false) {
        OverviewScreen(OverviewState.Loading, {}, {})
    }
}

@Preview(name = "Пустое состояние", widthDp = 393, heightDp = 850)
@Composable
private fun EmptyPreview() {
    FinanceTheme(darkTheme = false) {
        OverviewScreen(OverviewState.Empty, {}, {})
    }
}

@Preview(name = "Детали · светлая", widthDp = 393, heightDp = 850)
@Composable
private fun LightDetailPreview() {
    FinanceTheme(darkTheme = false) {
        TransactionDetailScreen(FinanceSamples.content.transactions[1], {})
    }
}

@Preview(name = "Детали · тёмная", uiMode = Configuration.UI_MODE_NIGHT_YES, widthDp = 393, heightDp = 850)
@Composable
private fun DarkDetailPreview() {
    FinanceTheme(darkTheme = true) {
        TransactionDetailScreen(FinanceSamples.content.transactions[1], {})
    }
}

@Preview(name = "Планшет · две колонки", widthDp = 900, heightDp = 650)
@Composable
private fun WideOverviewPreview() {
    FinanceTheme(darkTheme = false) {
        OverviewScreen(OverviewState.Ready(FinanceSamples.content), {}, {}, wide = true)
    }
}
