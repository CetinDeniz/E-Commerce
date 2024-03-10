package com.axuca.app.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class BaseListAdapter<V : ViewDataBinding, T>(
    @LayoutRes private val layoutID: Int,
    private val onBind: (binding: V, item: T) -> Unit
) : ListAdapter<T, BaseViewHolder<V, T>>(ItemDiff<T>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<V, T> {
        val binding = DataBindingUtil.inflate<V>(
            LayoutInflater.from(parent.context),
            layoutID,
            parent,
            false
        )
        return BaseViewHolder(binding, onBind)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<V, T>, position: Int) {
        holder.bind(getItem(position))
    }
}