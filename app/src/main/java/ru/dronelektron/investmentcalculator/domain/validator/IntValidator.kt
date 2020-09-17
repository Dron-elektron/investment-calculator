package ru.dronelektron.investmentcalculator.domain.validator

import ru.dronelektron.investmentcalculator.domain.FieldError

object IntValidator : Validator<String?, Int, FieldError>() {
    override fun checkRaw(item: String?) = if (item.isNullOrEmpty()) FieldError.EMPTY_FIELD else null

    override fun checkTransformed(item: Int) = if (item == 0) FieldError.ZERO_FIELD else null

    override fun transform(item: String?) = item?.toInt() ?: 0
}
