package com.axuca.app.util

import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

fun ViewPager2.addCarouselEffect() {
    clipChildren = false    // No clipping the left and right items
    clipToPadding = false   // Show the viewpager in full width without clipping the padding
    offscreenPageLimit = 2  // Render the left and right items

    val compositePageTransformer = ViewPager2.PageTransformer { page, position ->
        val r = 1 - abs(position)
        page.scaleY = (0.8f + r * 0.2f)
    }
    setPageTransformer(compositePageTransformer)
}