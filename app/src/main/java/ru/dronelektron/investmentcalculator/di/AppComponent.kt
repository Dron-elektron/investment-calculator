package ru.dronelektron.investmentcalculator.di

import dagger.Component
import ru.dronelektron.investmentcalculator.di.module.DomainModule
import ru.dronelektron.investmentcalculator.di.module.PresentationModule
import ru.dronelektron.investmentcalculator.presentation.investform.InvestFormFragment

@Component(modules = [DomainModule::class, PresentationModule::class])
interface AppComponent {
    fun inject(fragment: InvestFormFragment)
}
