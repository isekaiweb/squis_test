package com.android.developer.sequis_test.detail.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.android.developer.sequis_test.core.domain.model.Comment
import com.android.developer.sequis_test.databinding.CommentLayoutBinding

class CommentsViewHolder(
    private val binding: CommentLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun Comment.bind() {
        with(binding) {
            tvInitial.text = author.generateInitialName()
            tvAuthor.text = author
            tvContent.text = content
            tvCreated.text = createdAt
        }
    }

    private fun String.generateInitialName(): String {
        return runCatching {
            val splitGroupName = split(" ")
            val first = splitGroupName[0].take(1)
            val second = splitGroupName[1].take(1)

            "$first$second"
        }.getOrDefault(take(1))
    }

}