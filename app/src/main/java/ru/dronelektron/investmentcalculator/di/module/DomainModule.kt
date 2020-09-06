package ru.dronelektron.investmentcalculator.di.module

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.dronelektron.investmentcalculator.domain.usecase.CalculateProfitUseCase

@Module
class DomainModule {
    @Provides
    fun provideBackgroundDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    fun provideCalculateProfitUseCase(
        bgDispatcher: CoroutineDispatcher
    ) = CalculateProfitUseCase(bgDispatcher)
}
