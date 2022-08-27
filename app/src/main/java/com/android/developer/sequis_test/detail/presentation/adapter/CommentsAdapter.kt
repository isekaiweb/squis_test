package com.android.developer.sequis_test.detail.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.android.developer.sequis_test.core.domain.model.Comment
import com.android.developer.sequis_test.databinding.CommentLayoutBinding

class CommentsAdapter : ListAdapter<Comment, CommentsViewHolder>(
    object : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.createdAt == newItem.createdAt
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }
    }
) {

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.apply { getItem(position)?.bind() }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder =
        CommentsViewHolder(
            CommentLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
}
