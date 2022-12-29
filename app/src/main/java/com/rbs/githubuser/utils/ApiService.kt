package com.rbs.githubuser.utils

import com.rbs.githubuser.data.DetailUser
import com.rbs.githubuser.data.SearchUser
import com.rbs.githubuser.data.SearchUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    @Headers("Authorization: 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")
    fun getAllUsers(): Call<List<SearchUser>>

    @GET("search/users")
    @Headers("Authorization: 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")
    fun getUserResponse(@Query("q") username: String): Call<SearchUserResponse>

    @GET("users/{username}")
    @Headers("Authorization: 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")
    fun getDetailUser(@Path("username") username: String): Call<DetailUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: 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")
    fun getFollowerUser(@Path("username") username: String): Call<List<SearchUser>>

    @GET("users/{username}/following")
    @Headers("Authorization: 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")
    fun getFollowingUser(@Path("username") username: String): Call<List<SearchUser>>
}