package ru.dronelektron.investmentcalculator.presentation.investresult

import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.dronelektron.investmentcalculator.R
import ru.dronelektron.investmentcalculator.domain.model.Profit
import ru.dronelektron.investmentcalculator.presentation.investresult.profitslist.ProfitsAdapter
import java.util.*

private val DEFAULT_LOCALE = Locale.US

@BindingAdapter("profits")
fun setProfits(recycler: RecyclerView, profits: List<Profit>?) {
    (recycler.adapter as ProfitsAdapter).submitList(profits)
}

@BindingAdapter("visibleIf")
fun setVisibleIf(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("balanceFormat")
fun balanceFormat(view: TextView, balance: Double) {
    decimalFormat(view, R.string.profit_balance_format, balance)
}

@BindingAdapter("percentFormat")
fun percentFormat(view: TextView, percent: Double) {
    decimalFormat(view, R.string.profit_percent_format, percent)
}

private fun decimalFormat(view: TextView, @StringRes formatRes: Int, value: Double) {
    val textFormat = view.resources.getString(formatRes)

    view.text = String.format(DEFAULT_LOCALE, textFormat, value)
}
