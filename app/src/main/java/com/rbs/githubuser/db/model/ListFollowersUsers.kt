package com.rbs.githubuser.db.model

import android.os.Parcelable
import com.rbs.githubuser.data.SearchUser
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListFollowersUsers(
    val userList: List<SearchUser>
) : Parcelable