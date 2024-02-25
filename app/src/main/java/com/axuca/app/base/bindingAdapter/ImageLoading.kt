package com.axuca.app.base.bindingAdapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("loadImage")
fun ImageView.loadImage(imageURL: String) {
    Glide
        .with(this.context)
        .load(imageURL)
        .into(this)
}