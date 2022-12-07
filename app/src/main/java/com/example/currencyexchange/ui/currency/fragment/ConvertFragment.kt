package com.example.currencyexchange.ui.currency.fragment

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.currencyexchange.R
import com.example.currencyexchange.data.model.ConversionModel
import com.example.currencyexchange.databinding.FragmentConvertBinding
import com.example.currencyexchange.ui.base.BaseFragment
import com.example.currencyexchange.ui.currency.event.TimerEvent
import com.example.currencyexchange.ui.currency.viewmodel.CurrencyViewModel
import com.example.currencyexchange.ui.currency.dialog.ApprovalBottomSheetDialog
import com.example.currencyexchange.utils.showToast
import kotlinx.coroutines.launch

class ConvertFragment : BaseFragment<FragmentConvertBinding, CurrencyViewModel>(
    FragmentConvertBinding::inflate
) {
    private var data: ConversionModel? = null
    override val viewModel: CurrencyViewModel by activityViewModels()

    override fun initView(binding: FragmentConvertBinding, savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        arguments?.let {
            data = it.getParcelable("Data")
            binding.data = data
        }
        viewModel.startTimer()
        binding.btnConvert.setOnClickListener { showApprovalDialog() }
    }

    private fun showApprovalDialog() = childFragmentManager.let {
        data?.let { data ->
            viewModel.cancelTimer()
            ApprovalBottomSheetDialog.newInstance(object :
                ApprovalBottomSheetDialog.ItemClickListener {
                override fun onCancelClicked() {
                    showToast(requireContext(), "Transaction cancelled")
                    findNavController().navigate(R.id.action_convertFragment_to_currencySelectionFragment)
                }

                override fun onApprovalClick(data: ConversionModel) {
                    val direction =
                        ConvertFragmentDirections.actionConvertFragmentToApprovedFragment(data)
                    findNavController().navigate(direction)
                }
            }, data).apply {
                show(it, "Approval")
            }
        }
    }

    override fun observeViewModel(viewModel: CurrencyViewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.timerState.collect { timerState ->
                    // New value received
                    when (timerState) {
                        TimerEvent.Finish -> {
                            showToast(requireContext(), "Time finish, Please redo the conversion")
                            findNavController().navigate(R.id.action_convertFragment_to_currencySelectionFragment)
                            viewModel.setTimerToInitialState()
                        }
                        TimerEvent.InitialState -> {}
                        is TimerEvent.OnTick -> {
                            binding.tvTimer.text = timerState.timeValue
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.cancelTimer()
    }

}