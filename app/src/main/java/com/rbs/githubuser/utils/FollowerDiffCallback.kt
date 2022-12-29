package com.rbs.githubuser.utils

import androidx.recyclerview.widget.DiffUtil
import com.rbs.githubuser.data.SearchUser
import com.rbs.githubuser.db.DetailUserDB

class FollowerDiffCallback(
    private val oldList: List<SearchUser>,
    private val newList: List<SearchUser>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUsers = oldList[oldItemPosition]
        val newUsers = newList[newItemPosition]

        return oldUsers.url == newUsers.url && oldUsers.username == newUsers.username
    }
}