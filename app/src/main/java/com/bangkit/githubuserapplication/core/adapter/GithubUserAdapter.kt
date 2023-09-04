package com.bangkit.githubuserapplication.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.githubuserapplication.databinding.ItemUserBinding
import com.bangkit.githubuserapplication.data.source.remote.response.GithubUserResponse
import com.bangkit.githubuserapplication.domain.model.GithubUser
import com.bumptech.glide.Glide

class GithubUserAdapter(
    private val listGithubUser: ArrayList<GithubUser>
): RecyclerView.Adapter<GithubUserAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listGithubUser.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (_, username, avatarUrl) = listGithubUser[position]
        holder.binding.tvUsername.text = username
        Glide.with(holder.itemView.context)
            .load(avatarUrl)
            .into(holder.binding.imgProfile)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listGithubUser[holder.adapterPosition])
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: GithubUser)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}