package ru.dronelektron.investmentcalculator

import android.app.Application
import ru.dronelektron.investmentcalculator.di.AppComponent
import ru.dronelektron.investmentcalculator.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}
