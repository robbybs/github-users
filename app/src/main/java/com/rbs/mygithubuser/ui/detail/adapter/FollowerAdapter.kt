package com.rbs.mygithubuser.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rbs.mygithubuser.data.search.SearchUserItems
import com.rbs.mygithubuser.databinding.ItemUserBinding

class FollowerAdapter(private val user: List<SearchUserItems>) :
    RecyclerView.Adapter<FollowerAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(user[position])
    }

    override fun getItemCount(): Int = user.size

    inner class ListViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(users: SearchUserItems) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(users.userAvatar)
                    .apply(RequestOptions().override(60, 60))
                    .into(ivPhoto)
                tvName.text = users.username
                tvUrl.text = users.userGithubLink
            }
        }
    }
}