package ru.dronelektron.investmentcalculator.presentation.investform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.dronelektron.investmentcalculator.domain.FieldError
import ru.dronelektron.investmentcalculator.domain.model.Profit
import ru.dronelektron.investmentcalculator.domain.usecase.CalculateProfitUseCase
import ru.dronelektron.investmentcalculator.domain.validator.DoubleValidator
import ru.dronelektron.investmentcalculator.domain.validator.IntValidator
import ru.dronelektron.investmentcalculator.presentation.Event

class InvestFormViewModel(private val calculateProfit: CalculateProfitUseCase) : ViewModel() {
    private val _profits = MutableLiveData<List<Profit>>()
    private val _isProfitsLoading = MutableLiveData<Boolean>()
    private val _balanceError = MutableLiveData<FieldError>()
    private val _percentError = MutableLiveData<FieldError>()
    private val _iterationsError = MutableLiveData<FieldError>()
    private val _navigateToResultsEvent = MutableLiveData<Event<Unit>>()

    val balance = MutableLiveData<String>()
    val percent = MutableLiveData<String>()
    val iterations = MutableLiveData<String>()
    val profits: LiveData<List<Profit>> = _profits
    val isProfitsLoading: LiveData<Boolean> = _isProfitsLoading
    val balanceError: LiveData<FieldError> = _balanceError
    val percentError: LiveData<FieldError> = _percentError
    val iterationsError: LiveData<FieldError> = _iterationsError
    val navigateToResultsEvent: LiveData<Event<Unit>> = _navigateToResultsEvent

    fun calculateProfits() {
        _balanceError.value = DoubleValidator.check(balance.value)
        _percentError.value = DoubleValidator.check(percent.value)
        _iterationsError.value = IntValidator.check(iterations.value)

        if (_balanceError.value != null) return
        if (_percentError.value != null) return
        if (_iterationsError.value != null) return

        val balanceValue = DoubleValidator.transform(balance.value)
        val percentValue = DoubleValidator.transform(percent.value)
        val iterationsValue = IntValidator.transform(iterations.value)
        val params = CalculateProfitUseCase.Params(balanceValue, percentValue, iterationsValue)

        viewModelScope.launch {
            _profits.value = null
            _isProfitsLoading.value = true
            _profits.value = calculateProfit(params)
            _isProfitsLoading.value = false
        }

        _navigateToResultsEvent.value = Event(Unit)
    }
}
