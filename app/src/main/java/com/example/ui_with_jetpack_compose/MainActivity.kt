package com.example.ui_with_jetpack_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import com.example.ui_with_jetpack_compose.ui.finance.FinanceSamples
import com.example.ui_with_jetpack_compose.ui.finance.OverviewScreen
import com.example.ui_with_jetpack_compose.ui.finance.OverviewState
import com.example.ui_with_jetpack_compose.ui.theme.FinanceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinanceTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    OverviewScreen(
                        state = OverviewState.Ready(FinanceSamples.content),
                        onTransactionClick = {},
                        onFill = {},
                    )
                }
            }
        }
    }
}
