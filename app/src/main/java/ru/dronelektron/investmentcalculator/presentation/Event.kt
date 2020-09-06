package ru.dronelektron.investmentcalculator.presentation

class Event<T>(private val data: T) {
    private var isHandled = false

    fun getDataIfNotHandled() = if (isHandled) {
        null
    } else {
        isHandled = true
        data
    }
}
