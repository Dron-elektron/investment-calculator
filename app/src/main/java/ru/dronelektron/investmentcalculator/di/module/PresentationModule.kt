package ru.dronelektron.investmentcalculator.di.module

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import ru.dronelektron.investmentcalculator.di.annotation.InvestForm
import ru.dronelektron.investmentcalculator.domain.usecase.CalculateProfitUseCase
import ru.dronelektron.investmentcalculator.presentation.investform.InvestFormViewModelFactory

@Module
class PresentationModule {
    @Provides
    @InvestForm
    fun provideInvestFormViewModelFactory(
        calculateProfitUseCase: CalculateProfitUseCase
    ): ViewModelProvider.Factory = InvestFormViewModelFactory(calculateProfitUseCase)
}
