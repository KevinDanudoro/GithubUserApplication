package com.bangkit.githubuserapplication.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.githubuserapplication.core.database.FavoriteUser
import com.bangkit.githubuserapplication.databinding.ItemUserBinding
import com.bumptech.glide.Glide

class FavoriteUserAdapter(
    private val favoriteUserList: List<FavoriteUser>,
) : RecyclerView.Adapter<FavoriteUserAdapter.ListViewHolder>() {
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

    override fun getItemCount(): Int = favoriteUserList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (_, username, photoUrl) = favoriteUserList[position]
        holder.binding.tvUsername.text = username
        Glide.with(holder.itemView.context)
            .load(photoUrl)
            .into(holder.binding.imgProfile)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(favoriteUserList[holder.adapterPosition])
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: FavoriteUser)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}