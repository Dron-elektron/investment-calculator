package ru.dronelektron.investmentcalculator.presentation.investform

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verifyOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.dronelektron.investmentcalculator.domain.FieldError
import ru.dronelektron.investmentcalculator.domain.model.Profit
import ru.dronelektron.investmentcalculator.domain.usecase.CalculateProfitUseCase
import ru.dronelektron.investmentcalculator.presentation.Event

@ExperimentalCoroutinesApi
class InvestFormViewModelTest {
    private val testDispatcher = TestCoroutineDispatcher()
    private val calculateProfit = mockk<CalculateProfitUseCase>()
    private lateinit var viewModel: InvestFormViewModel

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        viewModel = InvestFormViewModel(calculateProfit)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        clearMocks(calculateProfit)
    }

    @Test
    fun calculateProfits_whenBalanceIsNull() {
        testBalance(null, FieldError.EMPTY_FIELD)
    }

    @Test
    fun calculateProfits_whenBalanceIsEmpty() {
        testBalance("", FieldError.EMPTY_FIELD)
    }

    @Test
    fun calculateProfits_whenBalanceIsZero() {
        testBalance("0", FieldError.ZERO_FIELD)
    }

    @Test
    fun calculateProfits_whenBalanceIsValid() {
        testBalance(DEFAULT_BALANCE, null)
    }

    @Test
    fun calculateProfits_whenPercentIsNull() {
        testPercent(null, FieldError.EMPTY_FIELD)
    }

    @Test
    fun calculateProfits_whenPercentIsEmpty() {
        testPercent("", FieldError.EMPTY_FIELD)
    }

    @Test
    fun calculateProfits_whenPercentIsZero() {
        testPercent("0", FieldError.ZERO_FIELD)
    }

    @Test
    fun calculateProfits_whenPercentIsValid() {
        testPercent(DEFAULT_PERCENT, null)
    }

    @Test
    fun calculateProfits_whenIterationsIsNull() {
        testIterations(null, FieldError.EMPTY_FIELD)
    }

    @Test
    fun calculateProfits_whenIterationsIsEmpty() {
        testIterations("", FieldError.EMPTY_FIELD)
    }

    @Test
    fun calculateProfits_whenIterationsIsZero() {
        testIterations("0", FieldError.ZERO_FIELD)
    }

    @Test
    fun calculateProfits_whenIterationsIsValid() {
        testPercent(DEFAULT_ITERATIONS, null)
    }

    @Test
    fun calculateProfits_whenAllFieldsAreValid() {
        val profitsLoadingObserver = spyk<Observer<Boolean>>()
        val profitsObserver = spyk<Observer<List<Profit>>>()
        val navigateToResultsObserver = spyk<Observer<Event<Unit>>>()
        val params = CalculateProfitUseCase.Params(1000.0, 2.5, 8)
        val expectedProfits = listOf(Profit(1, 1000.0, 2.5))

        coEvery { calculateProfit(params) } returns expectedProfits

        viewModel.balance.value = DEFAULT_BALANCE
        viewModel.percent.value = DEFAULT_PERCENT
        viewModel.iterations.value = DEFAULT_ITERATIONS
        viewModel.isProfitsLoading.observeForever(profitsLoadingObserver)
        viewModel.profits.observeForever(profitsObserver)
        viewModel.navigateToResultsEvent.observeForever(navigateToResultsObserver)

        viewModel.calculateProfits()

        verifyOrder {
            profitsObserver.onChanged(null)
            profitsLoadingObserver.onChanged(true)
            profitsObserver.onChanged(expectedProfits)
            profitsLoadingObserver.onChanged(false)
            navigateToResultsObserver.onChanged(Event(Unit))
        }

        viewModel.isProfitsLoading.removeObserver(profitsLoadingObserver)
        viewModel.profits.removeObserver(profitsObserver)
        viewModel.navigateToResultsEvent.removeObserver(navigateToResultsObserver)
    }

    private fun testBalance(balance: String?, error: FieldError?) {
        viewModel.balance.value = balance

        viewModel.calculateProfits()

        assertEquals(error, viewModel.balanceError.value)
    }

    private fun testPercent(percent: String?, error: FieldError?) {
        viewModel.percent.value = percent

        viewModel.calculateProfits()

        assertEquals(error, viewModel.percentError.value)
    }

    private fun testIterations(iterations: String?, error: FieldError?) {
        viewModel.iterations.value = iterations

        viewModel.calculateProfits()

        assertEquals(error, viewModel.iterationsError.value)
    }

    companion object {
        private const val DEFAULT_BALANCE = "1000"
        private const val DEFAULT_PERCENT = "2.5"
        private const val DEFAULT_ITERATIONS = "8"
    }
}
