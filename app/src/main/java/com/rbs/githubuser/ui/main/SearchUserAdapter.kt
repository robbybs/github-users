package com.rbs.githubuser.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rbs.githubuser.data.SearchUser
import com.rbs.githubuser.databinding.ItemUsersBinding

class SearchUserAdapter : RecyclerView.Adapter<SearchUserAdapter.ViewHolder>() {

    private val listData = ArrayList<SearchUser>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: ArrayList<SearchUser>) {
        listData.clear()
        listData.addAll(items)
        notifyDataSetChanged()
    }

    private var onItemClickCallback: OnItemClickCallback? = null

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
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    inner class ViewHolder(private val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(users: SearchUser) {
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
        fun onItemClicked(data: SearchUser)
    }
}