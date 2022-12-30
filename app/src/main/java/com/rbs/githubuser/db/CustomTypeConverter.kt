package com.rbs.githubuser.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.rbs.githubuser.db.model.ListFollowersUsers
import com.rbs.githubuser.db.model.ListFollowingUsers

class CustomTypeConverter {
    @TypeConverter
    fun fromFollowersToJSON(users: ListFollowersUsers): String = Gson().toJson(users)

    @TypeConverter
    fun fromJSONToFollowers(json: String): ListFollowersUsers = Gson().fromJson(json, ListFollowersUsers::class.java)

    @TypeConverter
    fun fromFollowingToJSON(users: ListFollowingUsers): String = Gson().toJson(users)

    @TypeConverter
    fun fromJSONToFollowing(json: String): ListFollowingUsers = Gson().fromJson(json, ListFollowingUsers::class.java)
}