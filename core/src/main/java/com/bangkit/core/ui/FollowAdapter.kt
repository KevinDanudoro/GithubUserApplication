package com.bangkit.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.core.databinding.ItemFollowBinding
import com.bangkit.core.domain.model.GithubUser
import com.bumptech.glide.Glide

class FollowAdapter(
    private val listFollow: List<GithubUser>
): RecyclerView.Adapter<FollowAdapter.ListViewHolder>() {
    class ListViewHolder(val binding: ItemFollowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemFollowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(binding)
    }

    override fun getItemCount():Int = listFollow.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (_, username, avatarUrl) = listFollow[position]
        holder.binding.tvFollowName.text = username
        Glide.with(holder.itemView.context)
            .load(avatarUrl)
            .into(holder.binding.imgFollow)
    }
}