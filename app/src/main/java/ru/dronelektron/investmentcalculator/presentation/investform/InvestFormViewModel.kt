package ru.dronelektron.investmentcalculator.presentation.investform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.dronelektron.investmentcalculator.domain.model.Profit
import ru.dronelektron.investmentcalculator.domain.usecase.CalculateProfitUseCase
import ru.dronelektron.investmentcalculator.presentation.Event
import ru.dronelektron.investmentcalculator.presentation.investform.error.BalanceError
import ru.dronelektron.investmentcalculator.presentation.investform.error.IterationsError
import ru.dronelektron.investmentcalculator.presentation.investform.error.PercentError

class InvestFormViewModel(
    private val calculateProfit: CalculateProfitUseCase
) : ViewModel() {
    private val _profits = MutableLiveData<List<Profit>>()
    private val _isProfitsLoading = MutableLiveData<Boolean>()
    private val _balanceError = MutableLiveData<BalanceError>()
    private val _percentError = MutableLiveData<PercentError>()
    private val _iterationsError = MutableLiveData<IterationsError>()
    private val _navigateToResultsEvent = MutableLiveData<Event<Unit>>()

    val balance = MutableLiveData<String>()
    val percent = MutableLiveData<String>()
    val iterations = MutableLiveData<String>()
    val profits: LiveData<List<Profit>> = _profits
    val isProfitsLoading: LiveData<Boolean> = _isProfitsLoading
    val balanceError: LiveData<BalanceError> = _balanceError
    val percentError: LiveData<PercentError> = _percentError
    val iterationsError: LiveData<IterationsError> = _iterationsError
    val navigateToResultsEvent: LiveData<Event<Unit>> = _navigateToResultsEvent

    fun calculateProfits() {
        val balanceValue = validateBalance() ?: return
        val percentValue = validatePercent() ?: return
        val iterationsValue = validateIterations() ?: return
        val params = CalculateProfitUseCase.Params(balanceValue, percentValue, iterationsValue)

        viewModelScope.launch {
            _profits.value = null
            _isProfitsLoading.value = true
            _profits.value = calculateProfit(params)
            _isProfitsLoading.value = false
        }

        _navigateToResultsEvent.value = Event(Unit)
    }

    private fun validateBalance(): Double? {
        val balanceValue = balance.value

        if (balanceValue.isNullOrEmpty()) {
            _balanceError.value = BalanceError.EMPTY_BALANCE
        } else {
            val balanceDouble = balanceValue.toDouble()

            if (balanceDouble == 0.0) {
                _balanceError.value = BalanceError.ZERO_BALANCE
            } else {
                _balanceError.value = null

                return balanceDouble
            }
        }

        return null
    }

    private fun validatePercent(): Double? {
        val percentValue = percent.value

        if (percentValue.isNullOrEmpty()) {
            _percentError.value = PercentError.EMPTY_PERCENT
        } else {
            val percentDouble = percentValue.toDouble()

            if (percentDouble == 0.0) {
                _percentError.value = PercentError.ZERO_PERCENT
            } else {
                _percentError.value = null

                return percentDouble
            }
        }

        return null
    }

    private fun validateIterations(): Int? {
        val iterationsValue = iterations.value

        if (iterationsValue.isNullOrEmpty()) {
            _iterationsError.value = IterationsError.EMPTY_ITERATIONS
        } else {
            val iterationsInt = iterationsValue.toInt()

            if (iterationsInt == 0) {
                _iterationsError.value = IterationsError.ZERO_ITERATIONS
            } else {
                _iterationsError.value = null

                return iterationsInt
            }
        }

        return null
    }
}
