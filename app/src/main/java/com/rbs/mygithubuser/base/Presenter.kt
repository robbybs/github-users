package com.rbs.mygithubuser.base

interface Presenter<V : View> {
    fun attachView(view: V)

    fun detachView()

    fun getView(): V?
}