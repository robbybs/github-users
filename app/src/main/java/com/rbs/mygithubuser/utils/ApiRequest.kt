package com.rbs.mygithubuser.utils

import com.rbs.mygithubuser.data.detail.DetailUser
import com.rbs.mygithubuser.data.search.SearchUserItems
import com.rbs.mygithubuser.data.search.SearchUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRequest {
    @GET("search/users")
    @Headers("Authorization: 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")
    fun getUserResponse(@Query("q") username: String): Call<SearchUserResponse>

    @GET("users/{username}")
    @Headers("Authorization: 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")
    fun getDetailUser(@Path("username") username: String): Call<DetailUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: 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")
    fun getFollowerUser(@Path("username") username: String): Call<List<SearchUserItems>>

    @GET("users/{username}/following")
    @Headers("Authorization: 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")
    fun getFollowingUser(@Path("username") username: String): Call<List<SearchUserItems>>
}