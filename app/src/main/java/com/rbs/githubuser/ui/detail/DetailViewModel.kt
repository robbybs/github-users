package com.rbs.githubuser.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rbs.githubuser.data.DetailUser
import com.rbs.githubuser.db.DetailUserDB
import com.rbs.githubuser.db.repository.DetailUserRepository
import com.rbs.githubuser.utils.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {
    val user = MutableLiveData<DetailUser>()
    lateinit var item: DetailUser
    private val detailUserRepository: DetailUserRepository = DetailUserRepository(application)

    fun insertData(users: DetailUserDB) {
        detailUserRepository.insert(users)
    }

    fun getData(): LiveData<DetailUser> = user

    fun getDetailDataByUsername(username: String) {
        val client = ApiConfig.getService().getDetailUser(username)
        client.enqueue(object: Callback<DetailUser> {
            override fun onResponse(call: Call<DetailUser>, detailUserResponse: Response<DetailUser>) {
                item = detailUserResponse.body()!!
                user.postValue(item)
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    class ViewModelFactory(private val application: Application) :
        ViewModelProvider.NewInstanceFactory() {
        companion object {
            @Volatile
            private var INSTANCE: ViewModelFactory? = null

            @JvmStatic
            fun getInstance(application: Application): ViewModelFactory {
                if (INSTANCE == null) {
                    synchronized(ViewModelFactory::class.java) {
                        INSTANCE = ViewModelFactory(application)
                    }
                }
                return INSTANCE as ViewModelFactory
            }
        }

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                return DetailViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}

