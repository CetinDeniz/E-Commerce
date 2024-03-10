package com.axuca.app.base.adapter

import androidx.recyclerview.widget.DiffUtil

class ItemDiff<T>: DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T & Any, newItem: T & Any) = oldItem === newItem

    override fun areContentsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
        return false
    }
}