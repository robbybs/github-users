package com.rbs.mygithubuser.data.search

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchUserItems(
    @SerializedName("login")
    val username: String?,
    @SerializedName("avatar_url")
    val userAvatar: String?,
    @SerializedName("html_url")
    val userGithubLink: String?,
) : Parcelable