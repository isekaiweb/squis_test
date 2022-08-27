package com.android.developer.sequis_test.home.presentation.adapter

import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.android.developer.sequis_test.core.presentation.extentions.toggleVisibility
import com.android.developer.sequis_test.databinding.PicturesFooterLayoutBinding


class PictureFooterViewHolder(private val binding: PicturesFooterLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private var callBack: (() -> Unit)? = null

    fun setCallBack(callBack: (() -> Unit)) {
        this.callBack = callBack
    }

    fun LoadState.bind() {
        with(binding) {

            loadingFrame.toggleVisibility(this@bind is LoadState.Loading)



            with(tvError) {
                if (this@bind is LoadState.Error) {
                    toggleVisibility(true)
                    text = this@bind.error.message
                }
            }

            with(btnRetry) {
                setOnClickListener { callBack?.let { click -> click() } }
                toggleVisibility(this@bind is LoadState.Error)
            }
        }
    }
}