package com.rbs.mygithubuser.ui.main.view

import com.rbs.mygithubuser.base.View
import com.rbs.mygithubuser.data.search.SearchUserItems

interface MainView : View {
    fun setService()

    fun setErrorMessage(message: String)

    fun setData(data: List<SearchUserItems>?)

    fun showLoading()

    fun stopLoading()
}