package ru.dronelektron.investmentcalculator.domain.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import ru.dronelektron.investmentcalculator.domain.model.Profit

@ExperimentalCoroutinesApi
class CalculateProfitUseCaseTest {
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)
    private val calculateProfit = CalculateProfitUseCase(testDispatcher)

    @After
    fun cleanUp() {
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun invoke_whenOneIteration() = testScope.runBlockingTest {
        val params = CalculateProfitUseCase.Params(1000.0, 2.5, 1)
        val profits = calculateProfit(params)

        assertEquals(1, profits.size)
        assertProfit(Profit(1025.0, 2.5), profits.first())
    }

    @Test
    fun invoke_whenEightIterations() = testScope.runBlockingTest {
        val params = CalculateProfitUseCase.Params(1000.0, 2.5, 8)
        val profits = calculateProfit(params)

        assertEquals(8, profits.size)
        assertProfit(Profit(1218.4, 21.84), profits.last())
    }

    private fun assertProfit(expectedProfit: Profit, actualProfit: Profit) {
        assertEquals(expectedProfit.balance, actualProfit.balance, EPS)
        assertEquals(expectedProfit.percent, actualProfit.percent, EPS)
    }

    companion object {
        private const val EPS = 1e-2
    }
}
