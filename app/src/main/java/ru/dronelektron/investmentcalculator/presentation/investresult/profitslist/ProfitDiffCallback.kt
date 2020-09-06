package ru.dronelektron.investmentcalculator.presentation.investresult.profitslist

import androidx.recyclerview.widget.DiffUtil
import ru.dronelektron.investmentcalculator.domain.model.Profit

object ProfitDiffCallback : DiffUtil.ItemCallback<Profit>() {
    override fun areItemsTheSame(oldItem: Profit, newItem: Profit) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Profit, newItem: Profit) = oldItem == newItem
}
