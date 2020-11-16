package com.example.hava_durumu.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object DataBindingUtils {
    @JvmStatic
    @BindingAdapter("loadWeatherImage")
    fun loadWeatherImage(imageView: ImageView, type: String?) {
        Glide.with(imageView.context)
            .load("https://www.metaweather.com/static/img/weather/png/$type.png")
            .into(imageView)
    }
}