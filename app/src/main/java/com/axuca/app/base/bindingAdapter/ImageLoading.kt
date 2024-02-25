package com.axuca.app.base.bindingAdapter

import android.animation.ValueAnimator
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable
import com.axuca.app.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

@BindingAdapter("loadImage")
fun ImageView.loadImage(imageURL: String) {
    val lottieDrawable = LottieDrawable()
    LottieCompositionFactory.fromRawRes(context, R.raw.loading).addListener { composition ->
        lottieDrawable.setComposition(composition)
        lottieDrawable.repeatCount = ValueAnimator.INFINITE
        lottieDrawable.start()
    }

    Glide.with(context)
        .load(imageURL)
        .placeholder(lottieDrawable)
        .error(R.drawable.ic_launcher_background)
        .transition(DrawableTransitionOptions.withCrossFade())
        .listener(
            object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    lottieDrawable.clearComposition()
                    return false
                }

            }
        )

        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}