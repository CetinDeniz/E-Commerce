package com.axuca.app.base.fragment

import android.graphics.PixelFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.axuca.app.base.viewmodel.BaseViewModel
import com.axuca.app.base.State
import com.axuca.app.databinding.LoadingOverlayBinding
import com.axuca.app.util.observe
import timber.log.Timber

abstract class BaseViewModelFragment<B : ViewDataBinding, V : BaseViewModel> : BaseFragment() {
    protected abstract val viewModel: V

    protected abstract val layoutResourceID: Int
    private var _binding: B? = null
    protected val binding: B get() = _binding!!

    private var loadingOverlay: LoadingOverlayBinding? = null

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
        if (loadingOverlay == null) {
            loadingOverlay = LoadingOverlayBinding.inflate(
                LayoutInflater.from(requireContext())
            )

            val params = WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT
            )

            requireActivity().windowManager.addView(loadingOverlay?.root, params)
            binding.root.visibility = View.INVISIBLE
        }
    }

    private fun hideFullScreenOverlay() {
        loadingOverlay?.let {
            loadingOverlay = null
            requireActivity().windowManager.removeViewImmediate(it.root)
            binding.root.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}