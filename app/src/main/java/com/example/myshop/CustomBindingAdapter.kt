package com.example.myshop

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:setHotItem")
fun setHotItem(imageView: ImageView, hotItem: Boolean){
    when(hotItem){
        true -> imageView.setImageResource(R.drawable.ic_baseline_favorite_true)
        false -> imageView.setImageResource(R.drawable.ic_baseline_favorite_false)
    }
}