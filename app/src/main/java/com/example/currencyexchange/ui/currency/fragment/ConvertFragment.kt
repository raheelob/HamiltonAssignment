package com.example.currencyexchange.ui.currency.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.currencyexchange.R
import com.example.currencyexchange.data.model.ConversionModel
import com.example.currencyexchange.databinding.FragmentConvertBinding
import com.example.currencyexchange.databinding.FragmentCurrencySelectionBinding
import com.example.currencyexchange.ui.base.BaseFragment
import com.example.currencyexchange.ui.currency.event.CurrencyDataEvent
import com.example.currencyexchange.ui.currency.viewmodel.CurrencyViewModel
import com.example.currencyexchange.utils.dialog.ApprovalBottomSheetDialog
import com.example.currencyexchange.utils.dialog.CurrencyBottomSheetDialog
import com.example.currencyexchange.utils.showToast
import kotlinx.coroutines.launch

class ConvertFragment : BaseFragment<FragmentConvertBinding, CurrencyViewModel>(
    FragmentConvertBinding::inflate
) {
    private var data: ConversionModel? = null
    private var countDownTimer: CountDownTimer? = null
    override val viewModel: CurrencyViewModel by activityViewModels()

    override fun initView(binding: FragmentConvertBinding, savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        arguments?.let {
            data = it.getParcelable("Data")
            binding.data = data
        }
        startTimer()
        binding.btnConvert.setOnClickListener { showApprovalDialog() }
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(31000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                binding.tvTimer.text = "" + millisUntilFinished / 1000 + " s"
            }

            override fun onFinish() {
                showToast(requireContext(), "Time finish, Please redo the conversion")
                findNavController().navigate(R.id.action_convertFragment_to_currencySelectionFragment)
            }
        }.start()
    }

    private fun showApprovalDialog() = childFragmentManager.let {
        data?.let { data ->
            countDownTimer?.cancel()
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
                show(it, "Approvale")
            }
        }
    }

    override fun observeViewModel(viewModel: CurrencyViewModel) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
    }

}