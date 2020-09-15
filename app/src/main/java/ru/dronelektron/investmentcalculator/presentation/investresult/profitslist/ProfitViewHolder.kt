package ru.dronelektron.investmentcalculator.presentation.investresult.profitslist

import androidx.recyclerview.widget.RecyclerView
import ru.dronelektron.investmentcalculator.databinding.ItemProfitBinding
import ru.dronelektron.investmentcalculator.domain.model.Profit

class ProfitViewHolder(private val binding: ItemProfitBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(profit: Profit) {
        binding.profit = profit
        binding.executePendingBindings()
    }
}
