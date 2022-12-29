package com.rbs.githubuser.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchUserResponse(
    @SerializedName("total_count")
    val totalCount: Int?,

    @SerializedName("incomplete_results")
    val incompleteResults: String?,

    @SerializedName("items")
    val userData: List<SearchUser>?,
) : Parcelable