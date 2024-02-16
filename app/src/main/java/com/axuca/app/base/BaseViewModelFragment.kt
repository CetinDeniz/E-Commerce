package com.axuca.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.axuca.app.ui.home.HomeViewModel

abstract class BaseViewModelFragment<B : ViewDataBinding, V : BaseViewModel> : BaseFragment() {
    protected abstract val viewModel: V

    protected abstract val layoutResourceID: Int

    private var _binding: B? = null
    protected val binding: B
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        inflateBinding(inflater, container)
        return binding.root
    }

    private fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = DataBindingUtil.inflate(inflater, layoutResourceID, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}