package com.rbs.mygithubuser.data.detail

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailUser(
    @SerializedName("login")
    val username: String?,
    @SerializedName("avatar_url")
    val userAvatar: String?,
    @SerializedName("html_url")
    val userGithubLink: String?,
    @SerializedName("name")
    val userFullName: String?,
    @SerializedName("company")
    val userCompany: String?,
    @SerializedName("location")
    val userLocation: String?,
    @SerializedName("public_repos")
    val userRepository: Int?,
    @SerializedName("followers")
    val userFollowers: Int?,
    @SerializedName("following")
    val userFollowing: Int?,
) : Parcelable