package com.android.developer.sequis_test.home.presentation.adapter

import androidx.core.view.ViewCompat
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.android.developer.sequis_test.core.domain.model.Picture
import com.android.developer.sequis_test.core.presentation.extentions.setImgUrl
import com.android.developer.sequis_test.databinding.PictureLayoutBinding

class PicturesViewHolder(
    private val binding: PictureLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {

    private var callBack: ((String, FragmentNavigator.Extras) -> Unit)? = null

    fun setCallBack(callBack: ((String, FragmentNavigator.Extras) -> Unit)) {
        this.callBack = callBack
    }

    fun Picture.bind() {
        with(binding) {
            ViewCompat.setTransitionName(img, "img_${id}")

            img.setImgUrl(url)
            tvAuthor.text = author

            itemView.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    img to "img_${id}"
                )
                callBack?.let { click -> click(this@bind.id, extras) }
            }
        }
    }
}