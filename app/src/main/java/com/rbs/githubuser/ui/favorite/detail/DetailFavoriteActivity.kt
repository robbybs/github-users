package com.rbs.githubuser.ui.favorite.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.rbs.githubuser.R
import com.rbs.githubuser.databinding.ActivityDetailFavoriteBinding
import com.rbs.githubuser.db.model.DetailUserDB

class DetailFavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailFavoriteBinding
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailFavoriteViewModel by viewModels<DetailFavoriteViewModel> {
            DetailFavoriteViewModel.ViewModelFactory.getInstance(application)
            DetailFavoriteViewModel.ViewModelFactory(application)
        }

        username = intent.getStringExtra(USERNAME).toString()

        detailFavoriteViewModel.getData(username).observe(this) {
            setLoadingData()
            setUsersData(it)
        }

        setPagerAdapter()
    }

    private fun setLoadingData() {
        binding.container.visibility = View.VISIBLE
    }

    private fun setUsersData(users: DetailUserDB) {
        setView(binding.tvName, users.name)
        setView(binding.tvUsername, users.username)
        setView(binding.tvUrl, users.url)
        setView(binding.tvCompany, users.company)
        setImage(binding.ivAvatar, users.avatar)
    }

    private fun setView(view: TextView, value: String?) {
        if (value != null) {
            view.text = value
        } else {
            view.visibility = View.GONE
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setImage(view: ImageView, value: String?) {
        if (value != null) {
            Glide.with(this)
                .load(value)
                .into(view)
        } else {
            view.visibility = View.GONE
        }
    }

    private fun setPagerAdapter() {
        val favoritePagerAdapter = FavoritePagerAdapter(this, username)
        val viewPager = binding.viewPager
        val tabs = binding.tabLayout

        binding.viewPager.adapter = favoritePagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    companion object {
        const val USERNAME = "username"

        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.title_followers, R.string.title_following)
    }
}