package ru.dronelektron.investmentcalculator.domain.validator

abstract class Validator<T, R, E> {
    fun check(item: T): E? {
        checkRaw(item)?.let { return it }
        checkTransformed(transform(item))?.let { return it }

        return null
    }

    protected abstract fun checkRaw(item: T): E?

    protected abstract fun checkTransformed(item: R): E?

    abstract fun transform(item: T): R
}
