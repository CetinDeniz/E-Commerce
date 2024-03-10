package com.axuca.app.base.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder<V: ViewDataBinding, T>(
    private val binding: V,
    private val onBind: (binding: V, item: T) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: T) {
        onBind(binding, item)
    }
}