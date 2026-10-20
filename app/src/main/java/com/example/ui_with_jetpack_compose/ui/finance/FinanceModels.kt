package com.example.ui_with_jetpack_compose.ui.finance

import java.text.NumberFormat
import java.util.Locale

data class Account(
    val name: String,
    val number: String,
    val balanceKopecks: Long,
    val kind: String,
)

data class FinanceTransaction(
    val id: String,
    val title: String,
    val category: String,
    val date: String,
    val time: String,
    val accountName: String,
    val amountKopecks: Long,
    val icon: TransactionIcon,
)

enum class TransactionIcon { Shopping, Coffee, Taxi, Income }

data class ExpenseCategory(val name: String, val amountKopecks: Long)

data class FinanceContent(
    val accounts: List<Account>,
    val transactions: List<FinanceTransaction>,
    val categories: List<ExpenseCategory>,
) {
    val totalKopecks: Long get() = accounts.sumOf(Account::balanceKopecks)
    val expensesKopecks: Long get() = categories.sumOf(ExpenseCategory::amountKopecks)
}

sealed interface OverviewState {
    data object Loading : OverviewState
    data object Empty : OverviewState
    data class Ready(val content: FinanceContent) : OverviewState
}

object FinanceSamples {
    val content = FinanceContent(
        accounts = listOf(
            Account("Текущий счёт", "•• 4832", 158_420_75, "Black"),
            Account("Накопительный", "•• 7109", 482_700_00, "Цель: путешествие"),
        ),
        transactions = listOf(
            FinanceTransaction("1", "Зарплата", "Пополнение", "Сегодня", "09:15", "Текущий счёт", 135_000_00, TransactionIcon.Income),
            FinanceTransaction("2", "ВкусВилл", "Супермаркеты", "Вчера", "19:42", "Текущий счёт", -2_840_50, TransactionIcon.Shopping),
            FinanceTransaction("3", "Кофемания", "Кафе и рестораны", "Вчера", "12:20", "Текущий счёт", -690_00, TransactionIcon.Coffee),
            FinanceTransaction("4", "Яндекс Go", "Транспорт", "15 октября", "21:08", "Текущий счёт", -1_125_00, TransactionIcon.Taxi),
        ),
        categories = listOf(
            ExpenseCategory("Покупки", 18_540_00),
            ExpenseCategory("Еда", 12_780_00),
            ExpenseCategory("Транспорт", 7_310_00),
            ExpenseCategory("Другое", 4_920_00),
        ),
    )
}

private val russianLocale = Locale.forLanguageTag("ru-RU")

fun formatRubles(kopecks: Long, showPlus: Boolean = false): String {
    val number = NumberFormat.getNumberInstance(russianLocale).apply {
        minimumFractionDigits = if (kopecks % 100 == 0L) 0 else 2
        maximumFractionDigits = 2
    }.format(kotlin.math.abs(kopecks) / 100.0)
    val sign = when {
        kopecks < 0 -> "−"
        showPlus && kopecks > 0 -> "+"
        else -> ""
    }
    return "$sign$number ₽"
}
