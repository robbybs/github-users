package com.rbs.githubuser.ui.favorite.detail.following

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rbs.githubuser.data.SearchUser
import com.rbs.githubuser.databinding.ItemUsersBinding
import com.rbs.githubuser.utils.SearchUserDiffCallback

class DetailFollowingAdapter : RecyclerView.Adapter<DetailFollowingAdapter.ViewHolder>() {

    private val listUsers = ArrayList<SearchUser>()

    fun setListUsers(listUsers: List<SearchUser>) {
        val diffCallback = SearchUserDiffCallback(this.listUsers, listUsers)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listUsers.clear()
        this.listUsers.addAll(listUsers)
        diffResult.dispatchUpdatesTo(this)
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
        fun bind(users: SearchUser) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(users.avatar)
                    .apply(RequestOptions().override(60, 60))
                    .into(civAvatar)
                tvUsername.text = users.username
                tvUrl.text = users.url
            }
        }
    }
}