package com.axuca.app.presentation.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.axuca.app.R
import com.axuca.app.base.adapter.BaseAdapter
import com.axuca.app.base.adapter.BaseListAdapter
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

        val productAdapter = BaseListAdapter<HomeProductItemBinding, Product>(R.layout.home_product_item) { binding, item ->
            binding.product = item
            binding.root.setOnClickListener {
                item.id?.let {
                    findNavController().navigate(
                        HomeFragmentDirections.actionNavigationHomeToProductDetailFragment(it)
                    )
                }
            }
        }

        binding.productsRecycler.adapter = productAdapter
    }

    private fun onStateChanged(homeState: HomeViewModel.HomeState?) {
        when(homeState) {
            is HomeViewModel.HomeState.AllProducts -> {
                (binding.productsRecycler.adapter as? BaseListAdapter<*, Product>)?.submitList(homeState.data)
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