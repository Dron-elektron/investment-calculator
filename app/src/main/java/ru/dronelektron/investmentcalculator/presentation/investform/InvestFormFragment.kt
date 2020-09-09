package ru.dronelektron.investmentcalculator.presentation.investform

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.dronelektron.investmentcalculator.App
import ru.dronelektron.investmentcalculator.R
import ru.dronelektron.investmentcalculator.databinding.FragmentInvestFormBinding
import ru.dronelektron.investmentcalculator.di.annotation.InvestForm
import ru.dronelektron.investmentcalculator.presentation.closeKeyboard
import ru.dronelektron.investmentcalculator.presentation.investform.error.BalanceError
import ru.dronelektron.investmentcalculator.presentation.investform.error.IterationsError
import ru.dronelektron.investmentcalculator.presentation.investform.error.PercentError
import javax.inject.Inject

class InvestFormFragment : Fragment() {
    private val investFormViewModel by activityViewModels<InvestFormViewModel> { investFormViewModelFactory }

    @Inject
    @InvestForm
    lateinit var investFormViewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity?.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentInvestFormBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.investFormViewModel = investFormViewModel

        investFormViewModel.navigateToResultsEvent.observe(viewLifecycleOwner, { event ->
            event.getDataIfNotHandled()?.let {
                navigateToResults()
                closeKeyboard()
            }
        })

        investFormViewModel.balanceError.observe(viewLifecycleOwner, { error ->
            binding.balanceLayout.error = when (error) {
                BalanceError.EMPTY_BALANCE -> getString(R.string.invest_form_error_empty_balance)
                BalanceError.ZERO_BALANCE -> getString(R.string.invest_form_error_zero_balance)
                else -> null
            }
        })

        investFormViewModel.percentError.observe(viewLifecycleOwner, { error ->
            binding.percentLayout.error = when (error) {
                PercentError.EMPTY_PERCENT -> getString(R.string.invest_form_error_empty_percent)
                PercentError.ZERO_PERCENT -> getString(R.string.invest_form_error_zero_percent)
                else -> null
            }
        })

        investFormViewModel.iterationsError.observe(viewLifecycleOwner, { error ->
            binding.iterationsLayout.error = when (error) {
                IterationsError.EMPTY_ITERATIONS -> getString(R.string.invest_form_error_empty_iterations)
                IterationsError.ZERO_ITERATIONS -> getString(R.string.invest_form_error_zero_iterations)
                else -> null
            }
        })

        return binding.root
    }

    private fun navigateToResults() {
        findNavController().navigate(R.id.action_invest_form_fragment_to_invest_result_fragment)
    }
}
