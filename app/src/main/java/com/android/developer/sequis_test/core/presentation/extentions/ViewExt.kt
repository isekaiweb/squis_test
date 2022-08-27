package com.android.developer.sequis_test.core.presentation.extentions

import android.view.View
import android.widget.ImageView
import com.android.developer.sequis_test.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun ImageView.setImgUrl(url: String) {
    Glide.with(this.context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_img_placeholder)
            .error(R.drawable.ic_img_broken)
    ).load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}


fun View.toggleVisibility(value: Boolean) {
    visibility = if (value) View.VISIBLE else View.GONE
}
