package com.example.currencyexchange.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.example.currencyexchange.utils.ProgressDialog
import com.example.currencyexchange.utils.hideDialog
import com.example.currencyexchange.utils.showDialog

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    internal lateinit var binding: VB
    protected abstract val viewModel: VM
    lateinit var mProgressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(binding, savedInstanceState)
        observeViewModel(viewModel)
    }

    abstract fun initView(binding: VB, savedInstanceState: Bundle?)

    abstract fun observeViewModel(viewModel: VM)

    protected fun isBindingInitialized() = ::binding.isInitialized

    internal fun hideLoading() {
        hideDialog(mProgressDialog)
    }

    internal fun showLoading() {
        showDialog(mProgressDialog)
    }

}