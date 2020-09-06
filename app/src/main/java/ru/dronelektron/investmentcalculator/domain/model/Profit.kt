package ru.dronelektron.investmentcalculator.domain.model

data class Profit(
    val iteration: Int,
    val balance: Double,
    val percent: Double
)
