package ru.dronelektron.investmentcalculator.presentation.investresult.profitslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.dronelektron.investmentcalculator.databinding.ItemProfitBinding
import ru.dronelektron.investmentcalculator.domain.model.Profit

class ProfitsAdapter : ListAdapter<Profit, ProfitViewHolder>(ProfitDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProfitBinding.inflate(inflater, parent, false)

        return ProfitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfitViewHolder, position: Int) {
        val profit = getItem(position)

        holder.bind(profit)
    }
}
