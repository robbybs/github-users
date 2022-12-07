package com.rbs.mygithubuser.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class User(
    var username: String? = null,

    var userAvatar: String? = null,

    var userGithubLink: String? = null,

    var fullname: String? = null,

    var repository: String? = null,

    var followers: String? = null,

    var company: String? = null,

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
) : Parcelable