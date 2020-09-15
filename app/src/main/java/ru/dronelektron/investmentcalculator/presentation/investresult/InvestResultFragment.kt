package ru.dronelektron.investmentcalculator.presentation.investresult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.dronelektron.investmentcalculator.databinding.FragmentInvestResultBinding
import ru.dronelektron.investmentcalculator.presentation.investform.InvestFormViewModel
import ru.dronelektron.investmentcalculator.presentation.investresult.profitslist.ProfitsAdapter

class InvestResultFragment : Fragment() {
    private val investFormViewModel by activityViewModels<InvestFormViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentInvestResultBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.investFormViewModel = investFormViewModel
        setupProfits(binding.profits)

        return binding.root
    }

    private fun setupProfits(recycler: RecyclerView) {
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = ProfitsAdapter()
    }
}
