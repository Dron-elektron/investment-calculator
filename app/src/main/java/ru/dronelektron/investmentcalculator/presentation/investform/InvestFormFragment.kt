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
import ru.dronelektron.investmentcalculator.domain.FieldError
import ru.dronelektron.investmentcalculator.presentation.closeKeyboard
import javax.inject.Inject

class InvestFormFragment : Fragment() {
    private val investFormViewModel by activityViewModels<InvestFormViewModel> { investFormViewModelFactory }
    private lateinit var binding: FragmentInvestFormBinding

    @Inject
    @InvestForm
    lateinit var investFormViewModelFactory: ViewModelProvider.Factory

    private val balanceErrorObserver = { error: FieldError? ->
        binding.balanceLayout.error = when (error) {
            FieldError.EMPTY_FIELD -> getString(R.string.invest_form_error_empty_balance)
            FieldError.ZERO_FIELD -> getString(R.string.invest_form_error_zero_balance)
            else -> null
        }
    }

    private val percentErrorObserver = { error: FieldError? ->
        binding.percentLayout.error = when (error) {
            FieldError.EMPTY_FIELD -> getString(R.string.invest_form_error_empty_percent)
            FieldError.ZERO_FIELD -> getString(R.string.invest_form_error_zero_percent)
            else -> null
        }
    }

    private val iterationsErrorObserver = { error: FieldError? ->
        binding.iterationsLayout.error = when (error) {
            FieldError.EMPTY_FIELD -> getString(R.string.invest_form_error_empty_iterations)
            FieldError.ZERO_FIELD -> getString(R.string.invest_form_error_zero_iterations)
            else -> null
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity?.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentInvestFormBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.investFormViewModel = investFormViewModel

        investFormViewModel.navigateToResultsEvent.observe(viewLifecycleOwner, { event ->
            event.getDataIfNotHandled()?.let {
                closeKeyboard()
                navigateToResults()
            }
        })

        investFormViewModel.balanceError.observe(viewLifecycleOwner, balanceErrorObserver)
        investFormViewModel.percentError.observe(viewLifecycleOwner, percentErrorObserver)
        investFormViewModel.iterationsError.observe(viewLifecycleOwner, iterationsErrorObserver)

        return binding.root
    }

    private fun navigateToResults() {
        findNavController().navigate(R.id.action_invest_form_fragment_to_invest_result_fragment)
    }
}
