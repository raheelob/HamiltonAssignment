package com.example.currencyexchange.ui.currency.fragment

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.currencyexchange.R
import com.example.currencyexchange.data.model.ConversionModel
import com.example.currencyexchange.databinding.FragmentApprovedBinding
import com.example.currencyexchange.ui.base.BaseFragment
import com.example.currencyexchange.ui.currency.viewmodel.CurrencyViewModel

class ApprovedFragment : BaseFragment<FragmentApprovedBinding, CurrencyViewModel>(
    FragmentApprovedBinding::inflate
) {
    private var data: ConversionModel? = null

    override fun initView(binding: FragmentApprovedBinding, savedInstanceState: Bundle?) {
        arguments?.let {
            data = it.getParcelable("Data")
            binding.data = data
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_approvedFragment_to_currencySelectionFragment)
                }
            })

    }

    override fun observeViewModel(viewModel: CurrencyViewModel) {
    }

    override val viewModel: CurrencyViewModel by activityViewModels()
}