package com.rbs.githubuser.ui.favorite.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rbs.githubuser.databinding.ItemUsersBinding
import com.rbs.githubuser.db.DetailUserDB
import com.rbs.githubuser.utils.DiffCallback

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private val listUsers = ArrayList<DetailUserDB>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setListUsers(listUsers: List<DetailUserDB>) {
        val diffCallback = DiffCallback(this.listUsers, listUsers)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listUsers.clear()
        this.listUsers.addAll(listUsers)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUsers[position])
    }

    override fun getItemCount(): Int = listUsers.size

    inner class ViewHolder(private val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(users: DetailUserDB) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(users.avatar)
                    .apply(RequestOptions().override(60, 60))
                    .into(civAvatar)
                tvUsername.text = users.username
                tvUrl.text = users.url

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(users) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DetailUserDB)
    }
}