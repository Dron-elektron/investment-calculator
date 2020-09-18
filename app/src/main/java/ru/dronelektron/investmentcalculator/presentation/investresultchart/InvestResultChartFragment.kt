package ru.dronelektron.investmentcalculator.presentation.investresultchart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.mikephil.charting.charts.LineChart
import ru.dronelektron.investmentcalculator.databinding.FragmentInvestResultChartBinding
import ru.dronelektron.investmentcalculator.presentation.investform.InvestFormViewModel

class InvestResultChartFragment : Fragment() {
    private val investFormViewModel by activityViewModels<InvestFormViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentInvestResultChartBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.investFormViewModel = investFormViewModel
        setupChart(binding.lineChart)

        return binding.root
    }

    private fun setupChart(chart: LineChart) {
        chart.description.text = ""
    }
}
