package com.rbs.mygithubuser.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rbs.mygithubuser.R
import com.rbs.mygithubuser.data.search.SearchUserItems
import com.rbs.mygithubuser.databinding.ActivityMainBinding
import com.rbs.mygithubuser.ui.detail.DetailActivity
import com.rbs.mygithubuser.ui.favorite.FavoriteActivity
import com.rbs.mygithubuser.ui.main.presenter.MainPresenter
import com.rbs.mygithubuser.ui.main.view.MainView
import com.rbs.mygithubuser.utils.RetrofitService

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SearchUserAdapter
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializationAdapter()
        initializePresenter()
        getSearchUser()

        binding.btnFavorite.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }
    }

    private fun initializationAdapter() {
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
    }

    override fun initializePresenter() {
        presenter = MainPresenter()
        presenter.attachView(this)
        presenter.initialization()
    }

    private fun getSearchUser() {
        binding.btnSearch.setOnClickListener {
            val username = binding.inputSearch.editText?.text?.trim().toString()
            if (username.isNotEmpty()) {
                showLoading()
                presenter.getUserData(username)
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.text_warning_input_username),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun setService() {
        val service = RetrofitService.getService()
        presenter.setService(service)
    }

    override fun setData(data: List<SearchUserItems>?) {
        adapter = SearchUserAdapter(data!!)
        binding.rvUsers.adapter = adapter
        adapter.setOnItemClickCallback(object : SearchUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: SearchUserItems) {
                val username = data.username
                if (username != null) {
                    startActivity(
                        Intent(this@MainActivity, DetailActivity::class.java).putExtra(
                            DetailActivity.USERNAME,
                            username
                        )
                    )
                }
            }
        })
    }

    override fun showLoading() {
        binding.progressLoading.visibility = View.VISIBLE
        binding.rvUsers.visibility = View.GONE
    }

    override fun stopLoading() {
        binding.progressLoading.visibility = View.GONE
        binding.rvUsers.visibility = View.VISIBLE
    }

    override fun setErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}