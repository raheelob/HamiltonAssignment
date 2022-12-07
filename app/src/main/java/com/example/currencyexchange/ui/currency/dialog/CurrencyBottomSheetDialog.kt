package com.example.currencyexchange.ui.currency.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.currencyexchange.databinding.DialogCurrencyPickerBinding
import com.example.currencyexchange.ui.currency.adapter.CurrencyListAdapter
import com.example.currencyexchange.utils.enums.CurrencyName
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CurrencyBottomSheetDialog(private var mListener: ItemClickListener) :
    BottomSheetDialogFragment() {

    private lateinit var binding: DialogCurrencyPickerBinding
    private lateinit var currencyListAdapter: CurrencyListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCurrencyPickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        onClose()
    }

    private fun onClose() {
        binding.btnClose.setOnClickListener {
            dialog?.dismiss()
            mListener.onCloseClicked()
        }
    }

    private fun setUpRecyclerView() {
        val currencyNames: ArrayList<String> = ArrayList()
        CurrencyName.values().forEach { currencyName -> currencyNames.add(currencyName.name) }
        binding.collectionList.apply {
            currencyListAdapter = CurrencyListAdapter(onCollectionListItemClicked(), currencyNames)
            adapter = currencyListAdapter
        }
    }

    private fun onCollectionListItemClicked() =
        CurrencyListAdapter.CurrencyItemClickListener { data ->
            dialog?.dismiss()
            mListener.onCurrencyClick(data)
        }

    interface ItemClickListener {
        fun onCurrencyClick(data: String) {}
        fun onCloseClicked() {}
    }

    companion object {
        @JvmStatic
        fun newInstance(mListener: ItemClickListener): CurrencyBottomSheetDialog {
            val fragment = CurrencyBottomSheetDialog(mListener)
            return fragment
        }
    }
}


