package ru.dronelektron.investmentcalculator.presentation.investform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.dronelektron.investmentcalculator.domain.usecase.CalculateProfitUseCase

class InvestFormViewModelFactory(
    private val calculateProfitUseCase: CalculateProfitUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) = InvestFormViewModel(calculateProfitUseCase) as T
}
