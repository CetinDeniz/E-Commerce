package com.axuca.app.presentation.productDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.axuca.app.R
import com.axuca.app.base.fragment.BaseViewModelFragment
import com.axuca.app.databinding.FragmentProductDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : BaseViewModelFragment<FragmentProductDetailBinding, ProductDetailViewModel>() {
    override val layoutResourceID = R.layout.fragment_product_detail
    override val viewModel: ProductDetailViewModel by viewModels()
    private val args: ProductDetailFragmentArgs by navArgs()

    override fun performInitialRequests() {
        viewModel.getProduct(args.id)
    }

    override fun afterInitUI() {

    }
}