package com.rbs.mygithubuser.ui.detail.view

import com.rbs.mygithubuser.base.View
import com.rbs.mygithubuser.data.search.SearchUserItems

interface FollowerView : View {
    fun setService()

    fun setErrorMessage(message: String)

    fun setData(user: List<SearchUserItems>?)

    fun stopLoading()
}