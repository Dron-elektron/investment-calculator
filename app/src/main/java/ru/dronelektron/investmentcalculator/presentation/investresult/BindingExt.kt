package ru.dronelektron.investmentcalculator.presentation.investresult

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.dronelektron.investmentcalculator.domain.model.Profit
import ru.dronelektron.investmentcalculator.presentation.investresult.profitslist.ProfitsAdapter

@BindingAdapter("profits")
fun setProfits(recycler: RecyclerView, profits: List<Profit>?) {
    (recycler.adapter as ProfitsAdapter).submitList(profits)
}

@BindingAdapter("visibleIf")
fun setVisibleIf(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}
