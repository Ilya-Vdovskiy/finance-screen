package com.example.ui_with_jetpack_compose.ui.finance

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.LocalTaxi
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun OverviewScreen(
    state: OverviewState,
    onTransactionClick: (FinanceTransaction) -> Unit,
    onFill: () -> Unit,
    modifier: Modifier = Modifier,
    wide: Boolean = false,
    themeMode: Int = 0,
    onThemeModeChange: (Int) -> Unit = {},
    demoMode: Int = 0,
    onDemoModeChange: (Int) -> Unit = {},
) {
    if (wide && state is OverviewState.Ready) {
        WideOverview(state.content, onTransactionClick, modifier, themeMode, onThemeModeChange, demoMode, onDemoModeChange)
        return
    }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        item { OverviewHeader(themeMode, onThemeModeChange, demoMode, onDemoModeChange) }
        when (state) {
            OverviewState.Loading -> {
                item { BalanceSkeleton() }
                item { SkeletonBlock(120) }
                item { SkeletonBlock(190) }
            }
            OverviewState.Empty -> item { EmptyOverview(onFill) }
            is OverviewState.Ready -> {
                val content = state.content
                item { BalanceHero(content.totalKopecks) }
                item { SectionTitle("Мои счета") }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        content.accounts.forEach { AccountCard(it) }
                    }
                }
                item { ExpenseBreakdown(content) }
                item { SectionTitle("Последние операции") }
                items(content.transactions, key = { it.id }) { transaction ->
                    TransactionRow(transaction, onClick = { onTransactionClick(transaction) })
                }
            }
        }
    }
}

@Composable
private fun WideOverview(
    content: FinanceContent,
    onTransactionClick: (FinanceTransaction) -> Unit,
    modifier: Modifier = Modifier,
    themeMode: Int,
    onThemeModeChange: (Int) -> Unit,
    demoMode: Int,
    onDemoModeChange: (Int) -> Unit,
) {
    Row(
        modifier = modifier.fillMaxSize().padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(22.dp),
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(top = 24.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            item { OverviewHeader(themeMode, onThemeModeChange, demoMode, onDemoModeChange) }
            item { BalanceHero(content.totalKopecks) }
            item { SectionTitle("Мои счета") }
            items(content.accounts) { AccountCard(it) }
        }
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(top = 24.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            item { ExpenseBreakdown(content) }
            item { SectionTitle("Последние операции") }
            items(content.transactions, key = { it.id }) { transaction ->
                TransactionRow(transaction, onClick = { onTransactionClick(transaction) })
            }
        }
    }
}

@Composable
private fun OverviewHeader(
    themeMode: Int,
    onThemeModeChange: (Int) -> Unit,
    demoMode: Int,
    onDemoModeChange: (Int) -> Unit,
) {
    var menuExpanded by remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.weight(1f)) {
            Text("Обзор", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Мои финансы", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.onBackground)
        }
        Box {
            Box(
                modifier = Modifier.size(44.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary)
                    .clickable { menuExpanded = true }.semantics { contentDescription = "Открыть настройки темы и демо-режима" },
                contentAlignment = Alignment.Center,
            ) {
                Text("И", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onPrimary)
            }
            DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
                Text("Оформление", modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                listOf("Как в системе", "Светлая тема", "Тёмная тема").forEachIndexed { index, title ->
                    DropdownMenuItem(
                        text = { Text(title) },
                        onClick = { onThemeModeChange(index); menuExpanded = false },
                        trailingIcon = { if (themeMode == index) Icon(Icons.Default.Check, null) },
                    )
                }
                HorizontalDivider()
                Text("Состояние экрана", modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                listOf("Наполнено", "Загрузка", "Пусто").forEachIndexed { index, title ->
                    DropdownMenuItem(
                        text = { Text(title) },
                        onClick = { onDemoModeChange(index); menuExpanded = false },
                        trailingIcon = { if (demoMode == index) Icon(Icons.Default.Check, null) },
                    )
                }
            }
        }
    }
}

@Composable
fun BalanceHero(totalKopecks: Long, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text("Все деньги", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary)
            Spacer(Modifier.height(5.dp))
            Text(
                formatRubles(totalKopecks),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(22.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(32.dp).clip(CircleShape).background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(Icons.Default.ArrowUpward, null, modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.onPrimary)
                }
                Spacer(Modifier.width(9.dp))
                Text("Финансы в порядке", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(title, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onBackground)
}

@Composable
fun AccountCard(account: Account, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(17.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(48.dp).clip(RoundedCornerShape(15.dp)).background(MaterialTheme.colorScheme.secondaryContainer),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(Icons.Default.CreditCard, null, tint = MaterialTheme.colorScheme.onSecondaryContainer)
                }
                Spacer(Modifier.width(13.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(account.name, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                    Text("${account.kind} · ${account.number}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
            Spacer(Modifier.height(13.dp))
            Text(formatRubles(account.balanceKopecks), style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
fun ExpenseBreakdown(content: FinanceContent, modifier: Modifier = Modifier) {
    val scheme = MaterialTheme.colorScheme
    val colors = listOf(scheme.primary, scheme.secondary, scheme.tertiary, scheme.outline)
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = scheme.surface),
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("Расходы в октябре", style = MaterialTheme.typography.titleLarge, color = scheme.onSurface)
            Text(formatRubles(content.expensesKopecks), style = MaterialTheme.typography.headlineMedium, color = scheme.onSurface)
            Spacer(Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(124.dp), contentAlignment = Alignment.Center) {
                    Canvas(
                        modifier = Modifier.fillMaxSize().semantics {
                            contentDescription = "Диаграмма расходов по четырём категориям"
                        },
                    ) {
                        val stroke = 16.dp.toPx()
                        val inset = stroke / 2
                        val chartSize = Size(size.width - stroke, size.height - stroke)
                        var start = -90f
                        if (content.expensesKopecks > 0) {
                            content.categories.forEachIndexed { index, category ->
                                val sweep = category.amountKopecks.toFloat() / content.expensesKopecks * 360f
                                drawArc(colors[index % colors.size], start, (sweep - 3f).coerceAtLeast(0f), false, Offset(inset, inset), chartSize, style = Stroke(stroke, cap = StrokeCap.Round))
                                start += sweep
                            }
                        }
                    }
                    Text("100%", style = MaterialTheme.typography.titleMedium, color = scheme.onSurface)
                }
                Spacer(Modifier.width(19.dp))
                Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.weight(1f)) {
                    var displayedPercent = 0
                    content.categories.forEachIndexed { index, category ->
                        val percent = if (content.expensesKopecks == 0L) 0 else if (index == content.categories.lastIndex) {
                            100 - displayedPercent
                        } else {
                            (category.amountKopecks * 100.0 / content.expensesKopecks).roundToInt()
                        }
                        displayedPercent += percent
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(9.dp).clip(CircleShape).background(colors[index % colors.size]))
                            Spacer(Modifier.width(8.dp))
                            Text(category.name, style = MaterialTheme.typography.bodyMedium, color = scheme.onSurfaceVariant, modifier = Modifier.weight(1f), maxLines = 1)
                            Text("$percent%", style = MaterialTheme.typography.labelLarge, color = scheme.onSurface)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionRow(transaction: FinanceTransaction, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val scheme = MaterialTheme.colorScheme
    val icon = transaction.iconImage()
    val positive = transaction.amountKopecks > 0
    Row(
        modifier = modifier.fillMaxWidth().clip(MaterialTheme.shapes.medium)
            .background(scheme.surface)
            .clickable(role = Role.Button, onClick = onClick)
            .semantics { contentDescription = "${transaction.title}, ${transaction.category}, ${formatRubles(transaction.amountKopecks, showPlus = true)}, ${transaction.date}. Открыть детали" }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.size(48.dp).clip(RoundedCornerShape(15.dp))
                .background(if (positive) scheme.tertiaryContainer else scheme.surfaceVariant),
            contentAlignment = Alignment.Center,
        ) {
            Icon(icon, null, tint = if (positive) scheme.onTertiaryContainer else scheme.onSurfaceVariant)
        }
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(transaction.title, style = MaterialTheme.typography.titleMedium, color = scheme.onSurface, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text("${transaction.category} · ${transaction.date}", style = MaterialTheme.typography.bodyMedium, color = scheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        Spacer(Modifier.width(8.dp))
        Column(horizontalAlignment = Alignment.End) {
            Text(formatRubles(transaction.amountKopecks, showPlus = true), style = MaterialTheme.typography.titleMedium, color = if (positive) scheme.tertiary else scheme.onSurface)
            Icon(Icons.AutoMirrored.Filled.ArrowForward, null, modifier = Modifier.size(16.dp), tint = scheme.onSurfaceVariant)
        }
    }
}

fun FinanceTransaction.iconImage(): ImageVector = when (icon) {
    TransactionIcon.Shopping -> Icons.Default.ShoppingBag
    TransactionIcon.Coffee -> Icons.Default.Coffee
    TransactionIcon.Taxi -> Icons.Default.LocalTaxi
    TransactionIcon.Income -> Icons.Default.ArrowDownward
}

@Composable
private fun EmptyOverview(onFill: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.large,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 28.dp, vertical = 46.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(Modifier.size(76.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Add, null, modifier = Modifier.size(36.dp), tint = MaterialTheme.colorScheme.onPrimaryContainer)
            }
            Spacer(Modifier.height(20.dp))
            Text("Пока здесь пусто", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onSurface)
            Spacer(Modifier.height(8.dp))
            Text("Добавьте демо-данные, чтобы увидеть счета, траты и операции.", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(24.dp))
            Button(onClick = onFill) { Text("Показать финансы") }
        }
    }
}

@Composable
private fun BalanceSkeleton() {
    Column(
        modifier = Modifier.fillMaxWidth().clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surface).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        SkeletonBar(110, 15)
        SkeletonBar(220, 36)
        Spacer(Modifier.height(10.dp))
        SkeletonBar(150, 20)
    }
}

@Composable
private fun SkeletonBlock(height: Int) {
    Box(Modifier.fillMaxWidth().height(height.dp).clip(MaterialTheme.shapes.large).background(MaterialTheme.colorScheme.surface)) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            SkeletonBar(140, 18)
            SkeletonBar(220, 14)
            SkeletonBar(180, 14)
        }
    }
}

@Composable
private fun SkeletonBar(width: Int, height: Int) {
    val transition = rememberInfiniteTransition(label = "Пульсация загрузки")
    val alpha by transition.animateFloat(
        initialValue = 0.45f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(900), RepeatMode.Reverse),
        label = "Яркость скелетона",
    )
    Box(Modifier.width(width.dp).height(height.dp).clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha)))
}
