package com.rbs.githubuser.utils

import androidx.recyclerview.widget.DiffUtil
import com.rbs.githubuser.db.model.DetailUserDB

class DetailUserDBDiffCallback(
    private val oldList: List<DetailUserDB>,
    private val newList: List<DetailUserDB>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].username == newList[newItemPosition].username

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUsers = oldList[oldItemPosition]
        val newUsers = newList[newItemPosition]

        return oldUsers.name == newUsers.name && oldUsers.username == newUsers.username
    }
}