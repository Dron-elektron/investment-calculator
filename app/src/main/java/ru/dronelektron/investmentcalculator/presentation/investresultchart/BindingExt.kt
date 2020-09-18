package ru.dronelektron.investmentcalculator.presentation.investresultchart

import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import ru.dronelektron.investmentcalculator.R
import ru.dronelektron.investmentcalculator.domain.model.Profit

@BindingAdapter("profitsChartData")
fun setProfitsChartData(view: LineChart, profits: List<Profit>?) {
    val entries = profits?.map { Entry(it.iteration.toFloat(), it.balance.toFloat()) } ?: emptyList()
    val balanceText = view.resources.getString(R.string.invest_result_chart_balance)
    val balanceDataSet = LineDataSet(entries, balanceText)
    val dataSets = LineData(balanceDataSet)

    balanceDataSet.color = ContextCompat.getColor(view.context, R.color.colorProfitChartLine)
    balanceDataSet.setDrawValues(false)
    balanceDataSet.setDrawCircles(false)

    view.data = dataSets
    view.invalidate()
}
