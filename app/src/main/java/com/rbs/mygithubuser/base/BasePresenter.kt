package com.rbs.mygithubuser.base

open class BasePresenter<V : View> : Presenter<V> {
    private var view: V? = null

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun getView(): V? = view

    fun isViewAttached() = view != null
}