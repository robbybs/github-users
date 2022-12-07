package com.rbs.mygithubuser.data.search

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchUserResponse(
    @SerializedName("total_count")
    val totalCount: Int?,
    @SerializedName("incomplete_results")
    val incompleteResults: String?,
    @SerializedName("items")
    val userData: List<SearchUserItems>?,
) : Parcelable