package com.axuca.app.ui.home

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.axuca.app.R
import com.axuca.app.base.BaseFragment
import com.axuca.app.base.BaseViewModelFragment
import com.axuca.app.databinding.FragmentHomeBinding
import com.axuca.app.util.addCarouselEffect
import com.axuca.app.util.observe
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseViewModelFragment<FragmentHomeBinding, HomeViewModel>() {

    override val viewModel by viewModels<HomeViewModel>()
    override val layoutResourceID = R.layout.fragment_home

    private val handler by lazy { Handler(Looper.getMainLooper()) }
    private val runnable by lazy {
        object : Runnable {
            override fun run() {
                val currentItem = binding.viewpager.currentItem
                binding.viewpager.setCurrentItem(currentItem + 1, true)

                handler.postDelayed(this, 3000) // Set the delay between scrolls
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.text.observe(viewLifecycleOwner) {
            binding.textHome.text = it
        }
        observe(viewModel.state, ::onStateChanged)

        val adapter = ViewPagerImageAdapter(
            listOf(R.color.black, android.R.color.holo_blue_bright, android.R.color.holo_purple, android.R.color.holo_green_dark, android.R.color.holo_red_dark)
        )
        binding.viewpager.isUserInputEnabled = false
        binding.viewpager.adapter = adapter
        binding.viewpager.addCarouselEffect()
        startAutoScroll()
    }

    private fun onStateChanged(homeState: HomeViewModel.HomeState?) {
        Snackbar.make(binding.root, homeState.toString(), Snackbar.LENGTH_SHORT).show()
    }

    private fun startAutoScroll() {
        handler.postDelayed(runnable, 3000L)
    }

    private fun stopAutoScroll() {
        handler.removeCallbacks(runnable)
    }

    override fun onPause() {
        super.onPause()
        stopAutoScroll()
    }
}