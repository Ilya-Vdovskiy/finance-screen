package com.example.ui_with_jetpack_compose

import com.example.ui_with_jetpack_compose.ui.finance.FinanceSamples
import com.example.ui_with_jetpack_compose.ui.finance.formatRubles
import org.junit.Assert.assertEquals
import org.junit.Test

class FinanceModelsTest {
    @Test
    fun formatsRublesWithKopecksAndDirection() {
        fun compact(value: String) = value.filterNot { it.isWhitespace() || Character.isSpaceChar(it) }
        assertEquals("158420,75₽", compact(formatRubles(158_420_75)))
        assertEquals("−2840,50₽", compact(formatRubles(-2_840_50)))
        assertEquals("+135000₽", compact(formatRubles(135_000_00, showPlus = true)))
    }

    @Test
    fun totalBalanceAndExpensesMatchCards() {
        val content = FinanceSamples.content
        assertEquals(64_112_075L, content.totalKopecks)
        assertEquals(4_355_000L, content.expensesKopecks)
    }
}
