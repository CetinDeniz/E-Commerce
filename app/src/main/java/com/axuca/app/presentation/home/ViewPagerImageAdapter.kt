package com.axuca.app.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.axuca.app.R
import com.axuca.app.databinding.ViewPager2ItemLayoutBinding

class ViewPagerImageAdapter(
    private val images: List<Int>
) : RecyclerView.Adapter<ViewPagerImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = DataBindingUtil.inflate<ViewPager2ItemLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.view_pager2_item_layout,
            parent,
            false
        )

        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        if(images.isNotEmpty()) {
            holder.bind(images[position % images.size]) // position
        }
    }

    override fun getItemCount(): Int {
        return if (images.isNotEmpty()) Int.MAX_VALUE else 0 // images.size
    }

    class ImageViewHolder(private val binding: ViewPager2ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(resID: Int) {
            val color = ContextCompat.getColor(binding.root.context, resID)
            binding.image.setBackgroundColor(color)
        }
    }
}