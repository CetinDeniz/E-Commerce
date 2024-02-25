package com.axuca.app.presentation.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.axuca.app.R
import com.axuca.app.base.adapter.BaseAdapter
import com.axuca.app.base.fragment.BaseViewModelFragment
import com.axuca.app.domain.model.Product
import com.axuca.app.databinding.FragmentHomeBinding
import com.axuca.app.databinding.HomeProductItemBinding
import com.axuca.app.common.addCarouselEffect
import com.axuca.app.common.observe
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

        val adapter2 = object: BaseAdapter<HomeProductItemBinding, Product>(R.layout.home_product_item) {
            override fun bind(binding: HomeProductItemBinding, item: Product) {
                binding.product = item
                binding.root.setOnClickListener {
                    findNavController().navigate(
                        HomeFragmentDirections.actionNavigationHomeToProductDetailFragment()
                    )
                }
            }
        }
        binding.productsRecycler.adapter = adapter2
    }

    private fun onStateChanged(homeState: HomeViewModel.HomeState?) {
        when(homeState) {
            is HomeViewModel.HomeState.AllProducts -> {
                (binding.productsRecycler.adapter as? BaseAdapter<*, Product>)?.submitItems(homeState.data)
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