package com.axuca.app.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.viewModels
import com.axuca.app.R
import com.axuca.app.base.fragment.BaseViewModelFragment
import com.axuca.app.databinding.FragmentHomeBinding
import com.axuca.app.util.addCarouselEffect
import com.axuca.app.util.observe
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Runnable

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
        when(homeState) {
            is HomeViewModel.HomeState.AllProducts -> {
                Snackbar.make(binding.root, homeState.data.size.toString(), Snackbar.LENGTH_SHORT).show()
            }
            else -> {}
        }
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