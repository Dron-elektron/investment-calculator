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
import ru.dronelektron.investmentcalculator.presentation.closeKeyboard
import javax.inject.Inject

class InvestFormFragment : Fragment() {
    private val investFormViewModel by activityViewModels<InvestFormViewModel> { investFormViewModelFactory }

    @Inject
    lateinit var investFormViewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity?.applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentInvestFormBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.investFormViewModel = investFormViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        investFormViewModel.navigateToResultsEvent.observe(viewLifecycleOwner, { data ->
            data.getDataIfNotHandled()?.let {
                navigateToResults()
                closeKeyboard()
            }
        })
    }

    private fun navigateToResults() {
        findNavController().navigate(R.id.action_invest_form_fragment_to_invest_result_fragment)
    }
}
