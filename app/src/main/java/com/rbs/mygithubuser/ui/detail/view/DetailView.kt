package com.rbs.mygithubuser.ui.detail.view

import com.rbs.mygithubuser.base.View
import com.rbs.mygithubuser.data.detail.DetailUser
import com.rbs.mygithubuser.db.User

interface DetailView : View {
    fun setService()

    fun setErrorMessage(message: String)

    fun setData(user: DetailUser?)

    fun stopLoading()
}