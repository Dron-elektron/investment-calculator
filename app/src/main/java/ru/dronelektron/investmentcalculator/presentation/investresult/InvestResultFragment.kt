package ru.dronelektron.investmentcalculator.presentation.investresult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.dronelektron.investmentcalculator.R
import ru.dronelektron.investmentcalculator.databinding.FragmentInvestResultBinding
import ru.dronelektron.investmentcalculator.presentation.investform.InvestFormViewModel
import ru.dronelektron.investmentcalculator.presentation.investresult.profitslist.ProfitsAdapter

class InvestResultFragment : Fragment() {
    private val investFormViewModel by activityViewModels<InvestFormViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentInvestResultBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.investFormViewModel = investFormViewModel
        setupProfits(binding.profits)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_invest_result, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_chart) {
            findNavController().navigate(R.id.action_invest_result_fragment_to_invest_result_chart_fragment)

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupProfits(recycler: RecyclerView) {
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = ProfitsAdapter()
    }
}
