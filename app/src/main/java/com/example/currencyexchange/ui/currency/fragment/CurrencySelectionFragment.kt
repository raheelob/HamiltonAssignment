package com.example.currencyexchange.ui.currency.fragment


import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.currencyexchange.databinding.FragmentCurrencySelectionBinding
import com.example.currencyexchange.ui.base.BaseFragment
import com.example.currencyexchange.ui.currency.viewmodel.CurrencyViewModel
import com.example.currencyexchange.utils.ProgressDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrencySelectionFragment : BaseFragment<FragmentCurrencySelectionBinding, CurrencyViewModel>(
    FragmentCurrencySelectionBinding::inflate
) {
    override val viewModel: CurrencyViewModel by activityViewModels()

    override fun initView(binding: FragmentCurrencySelectionBinding, savedInstanceState: Bundle?) {
        mProgressDialog = ProgressDialog(requireActivity())
        viewModel.fetchCurrencyExchangeRate("USD")
    }

    override fun observeViewModel(viewModel: CurrencyViewModel) {
      viewLifecycleOwner.lifecycleScope.launch{
          repeatOnLifecycle(Lifecycle.State.STARTED){
              viewModel.currencyExchangeRateTasksEvent.collect{

              }
          }
      }
    }
}