package ru.dronelektron.investmentcalculator.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.dronelektron.investmentcalculator.domain.model.Profit
import kotlin.math.pow

class CalculateProfitUseCase(private val bgDispatcher: CoroutineDispatcher) {
    suspend operator fun invoke(params: Params) = withContext(bgDispatcher) {
        mutableListOf<Profit>().also { list ->
            for (iteration in 1..params.iterations) {
                val multiplier = getMultiplier(params.percent, iteration)
                val profitBalance = params.balance * multiplier
                val profitPercent = (profitBalance - params.balance) / params.balance * 100.0

                list.add(Profit(profitBalance, profitPercent))
            }
        }
    }

    private fun getMultiplier(percent: Double, iteration: Int) = (1.0 + percent / 100.0).pow(iteration)

    data class Params(
        val balance: Double,
        val percent: Double,
        val iterations: Int
    )
}
