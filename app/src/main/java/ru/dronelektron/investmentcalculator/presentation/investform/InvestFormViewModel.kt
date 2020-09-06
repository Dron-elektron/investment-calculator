package ru.dronelektron.investmentcalculator.presentation.investform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.dronelektron.investmentcalculator.domain.model.Profit
import ru.dronelektron.investmentcalculator.domain.usecase.CalculateProfitUseCase
import ru.dronelektron.investmentcalculator.presentation.Event

class InvestFormViewModel(
    private val calculateProfitUseCase: CalculateProfitUseCase
) : ViewModel() {
    private val _profits = MutableLiveData<List<Profit>>()
    private val _isProfitsLoading = MutableLiveData<Boolean>()
    private val _navigateToResultsEvent = MutableLiveData<Event<Unit>>()

    val balance = MutableLiveData<String>()
    val percent = MutableLiveData<String>()
    val iterations = MutableLiveData<String>()
    val profits: LiveData<List<Profit>> = _profits
    val isProfitsLoading: LiveData<Boolean> = _isProfitsLoading
    val navigateToResultsEvent: LiveData<Event<Unit>> = _navigateToResultsEvent

    fun calculateProfits() {
        val balanceValue = requireNotNull(balance.value).toDouble()
        val percentValue = requireNotNull(percent.value).toDouble()
        val iterationsValue = requireNotNull(iterations.value).toInt()
        val params = CalculateProfitUseCase.Params(balanceValue, percentValue, iterationsValue)

        viewModelScope.launch {
            _profits.value = null
            _isProfitsLoading.value = true
            _profits.value = calculateProfitUseCase(params)
            _isProfitsLoading.value = false
        }

        _navigateToResultsEvent.value = Event(Unit)
    }
}
