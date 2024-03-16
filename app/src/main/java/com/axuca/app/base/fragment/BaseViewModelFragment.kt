package com.axuca.app.base.fragment

import android.app.ProgressDialog.show
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.axuca.app.R
import com.axuca.app.base.viewmodel.BaseViewModel
import com.axuca.app.base.State
import com.axuca.app.databinding.LoadingOverlayBinding
import com.axuca.app.common.observe
import timber.log.Timber

abstract class BaseViewModelFragment<B : ViewDataBinding, V : BaseViewModel> : BaseFragment() {
    protected abstract val viewModel: V

    protected abstract val layoutResourceID: Int
    private var _binding: B? = null
    protected val binding: B get() = _binding!!

    private val loadingOverlay: LoadingOverlayBinding by lazy {
        LoadingOverlayBinding.inflate(LayoutInflater.from(requireContext()))
    }
    private val dialog: AlertDialog by lazy {
        AlertDialog.Builder(requireContext()).create().apply {
            setView(loadingOverlay.root)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        inflateBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        observeBaseViewModelState()
    }

    private fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = DataBindingUtil.inflate(inflater, layoutResourceID, container, false)
    }

    private fun observeBaseViewModelState() {
        observe(viewModel.baseState, ::onStateChanged)
    }

    private fun onStateChanged(state: State?) {
        when (state) {
            is State.Loading -> onLoading()
            is State.Success -> onSuccess()
            is State.Error -> onError(state.throwable)
            else -> {}
        }
    }

    private fun onLoading() {
        Timber.d("Loading")
        showFullScreenOverlay()
    }

    private fun onSuccess() {
        Timber.d("Success")
        hideFullScreenOverlay()
    }

    private fun onError(throwable: Throwable) {
        Timber.d("Error : $throwable")
    }

    private fun showFullScreenOverlay() {
        binding.root.visibility = View.INVISIBLE
        dialog.show()
    }

    private fun hideFullScreenOverlay() {
        dialog.dismiss()
        binding.root.visibility = View.VISIBLE

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}