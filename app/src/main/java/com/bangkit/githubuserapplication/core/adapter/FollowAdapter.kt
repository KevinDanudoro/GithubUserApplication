package com.bangkit.githubuserapplication.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.githubuserapplication.databinding.ItemFollowBinding
import com.bangkit.githubuserapplication.core.gson.GithubFollowResponseItem
import com.bumptech.glide.Glide

class FollowAdapter(
    private val listFollow: ArrayList<GithubFollowResponseItem>
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
        val (avatarUrl, username) = listFollow[position]
        holder.binding.tvFollowName.text = username
        Glide.with(holder.itemView.context)
            .load(avatarUrl)
            .into(holder.binding.imgFollow)
    }
}