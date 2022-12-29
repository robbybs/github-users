package com.rbs.githubuser.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailUser(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("login")
    val username: String?,

    @SerializedName("avatar_url")
    val avatar: String?,

    @SerializedName("html_url")
    val url: String?,

    @SerializedName("company")
    val company: String?
) : Parcelable