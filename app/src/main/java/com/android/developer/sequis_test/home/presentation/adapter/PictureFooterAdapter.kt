package com.android.developer.sequis_test.home.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.android.developer.sequis_test.databinding.PicturesFooterLayoutBinding

class PictureFooterAdapter(private val eventClick: () -> Unit) :
    LoadStateAdapter<PictureFooterViewHolder>() {


    override fun onBindViewHolder(holder: PictureFooterViewHolder, loadState: LoadState) {
        holder.apply { loadState.bind() }.setCallBack(eventClick)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PictureFooterViewHolder = PictureFooterViewHolder(
        PicturesFooterLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

}