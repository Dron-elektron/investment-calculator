package ru.dronelektron.investmentcalculator.domain.validator

import ru.dronelektron.investmentcalculator.domain.FieldError

object DoubleValidator : Validator<String?, Double, FieldError>() {
    override fun checkRaw(item: String?) = if (item.isNullOrEmpty()) FieldError.EMPTY_FIELD else null

    override fun checkTransformed(item: Double) = if (item == 0.0) FieldError.ZERO_FIELD else null

    override fun transform(item: String?) = item?.toDouble() ?: 0.0
}
