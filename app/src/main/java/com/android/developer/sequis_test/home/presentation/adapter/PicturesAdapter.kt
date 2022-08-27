package com.android.developer.sequis_test.home.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigator
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.android.developer.sequis_test.core.domain.model.Picture
import com.android.developer.sequis_test.databinding.PictureLayoutBinding

class PicturesAdapter(
    private val getIdPicture: (String, FragmentNavigator.Extras) -> Unit
) : PagingDataAdapter<Picture, PicturesViewHolder>(
    object : DiffUtil.ItemCallback<Picture>() {
        override fun areItemsTheSame(oldItem: Picture, newItem: Picture): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Picture, newItem: Picture): Boolean {
            return oldItem == newItem
        }
    }
) {

    override fun onBindViewHolder(holder: PicturesViewHolder, position: Int) {
        holder.apply { getItem(position)?.bind() }.setCallBack(getIdPicture)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturesViewHolder =
        PicturesViewHolder(
            PictureLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

}