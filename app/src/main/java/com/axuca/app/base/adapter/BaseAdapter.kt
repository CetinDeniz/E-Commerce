package com.axuca.app.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class BaseAdapter<V : ViewDataBinding, T>(
    @LayoutRes private val layoutID: Int,
    private val onBind: (binding: V, item: T) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<V, T>>() {

    private val items: ArrayList<T> = arrayListOf()

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
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun submitItems(newItems: List<T>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}