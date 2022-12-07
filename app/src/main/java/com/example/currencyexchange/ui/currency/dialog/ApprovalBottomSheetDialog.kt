package com.example.currencyexchange.ui.currency.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.currencyexchange.data.model.ConversionModel
import com.example.currencyexchange.databinding.DialogApprovalBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ApprovalBottomSheetDialog(
    private var mListener: ItemClickListener,
    private var data: ConversionModel
) : BottomSheetDialogFragment() {

    private lateinit var binding: DialogApprovalBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogApprovalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.data = data
        binding.btnCancel.setOnClickListener { mListener.onCancelClicked() }
        binding.btnApprove.setOnClickListener { mListener.onApprovalClick(data) }
    }

    interface ItemClickListener {
        fun onApprovalClick(data: ConversionModel) {}
        fun onCancelClicked() {}
    }

    companion object {
        @JvmStatic
        fun newInstance(
            mListener: ItemClickListener,
            data: ConversionModel
        ): ApprovalBottomSheetDialog {
            val fragment = ApprovalBottomSheetDialog(mListener, data)
            fragment.isCancelable = false
            return fragment
        }
    }
}


