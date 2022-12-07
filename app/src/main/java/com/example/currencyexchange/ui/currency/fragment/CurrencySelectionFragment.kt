package com.example.currencyexchange.ui.currency.fragment


import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.currencyexchange.R
import com.example.currencyexchange.data.model.ConversionModel
import com.example.currencyexchange.databinding.FragmentCurrencySelectionBinding
import com.example.currencyexchange.ui.base.BaseFragment
import com.example.currencyexchange.ui.currency.event.CurrencyDataEvent
import com.example.currencyexchange.ui.currency.viewmodel.CurrencyViewModel
import com.example.currencyexchange.utils.ProgressDialog
import com.example.currencyexchange.utils.dialog.CurrencyBottomSheetDialog
import com.example.currencyexchange.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import junit.runner.Version.id
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrencySelectionFragment : BaseFragment<FragmentCurrencySelectionBinding, CurrencyViewModel>(
    FragmentCurrencySelectionBinding::inflate
) {
    override val viewModel: CurrencyViewModel by activityViewModels()

    override fun initView(binding: FragmentCurrencySelectionBinding, savedInstanceState: Bundle?) {
        mProgressDialog = ProgressDialog(requireActivity())
        binding.viewModel = viewModel
        binding.tvTo.setOnClickListener { toCurrency()}
        binding.tvFrom.setOnClickListener {fromCurrency()}
        binding.ivArrows.setOnClickListener {swapCurrency()}
    }

    private fun swapCurrency() {
        val fromText = binding.tvFrom.text.trim()
        val toText = binding.tvTo.text.trim()
        binding.tvFrom.text = toText
        binding.tvTo.text = fromText
    }

    private fun fromCurrency() =  childFragmentManager.let {
        CurrencyBottomSheetDialog.newInstance(object :
            CurrencyBottomSheetDialog.ItemClickListener {
            override fun onCloseClicked() {
            }

            override fun onCurrencyClick(data: String) {
                if(data == binding.tvTo.text){
                    showToast(requireContext(), "Please select another currency")
                }else
              binding.tvFrom.text = data
            }
        }).apply {
            show(it, "FromCurrency")
        }
    }

    private fun toCurrency() =  childFragmentManager.let {
        CurrencyBottomSheetDialog.newInstance(object :
            CurrencyBottomSheetDialog.ItemClickListener {
            override fun onCloseClicked() {
            }

            override fun onCurrencyClick(data: String) {
                if(data == binding.tvFrom.text){
                    showToast(requireContext(), "Please select another currency")
                }else
                binding.tvTo.text = data
            }
        }).apply {
            show(it, "ToCurrency")
        }
    }

    override fun observeViewModel(viewModel: CurrencyViewModel) {
      viewLifecycleOwner.lifecycleScope.launch{
          repeatOnLifecycle(Lifecycle.State.STARTED){
              viewModel.currencyExchangeRateTasksEvent.collect{ eventState ->
                      when (eventState) {
                          is CurrencyDataEvent.ConvertedAmount ->{
                              val direction = CurrencySelectionFragmentDirections.actionCurrencySelectionFragmentToConvertFragment(eventState.data)
                              findNavController().navigate(direction)
                          }
                          is CurrencyDataEvent.Error -> {}
                          CurrencyDataEvent.Loading -> {}
                          CurrencyDataEvent.RemoteErrorByNetwork -> {}
                      }
              }
          }
      }
    }

}