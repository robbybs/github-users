package com.rbs.githubuser.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.rbs.githubuser.R
import com.rbs.githubuser.data.DetailUser
import com.rbs.githubuser.data.SearchUser
import com.rbs.githubuser.databinding.ActivityDetailBinding
import com.rbs.githubuser.db.model.DetailUserDB
import com.rbs.githubuser.db.model.ListFollowersUsers
import com.rbs.githubuser.db.model.ListFollowingUsers

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var username: String
    private val detailViewModel by viewModels<DetailViewModel> {
        DetailViewModel.ViewModelFactory.getInstance(application)
        DetailViewModel.ViewModelFactory(application)
    }

    private var listFollowers = ArrayList<SearchUser>()
    private var listFollowing = ArrayList<SearchUser>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        username = intent.getStringExtra(USERNAME).toString()
        setViewModel()
    }

    private fun setViewModel() {
        detailViewModel.getDetailDataByUsername(username)
        detailViewModel.getData().observe(this) {
            setLoadingData()
            setUsersData(it)
        }

        detailViewModel.getFollowers(username)
        detailViewModel.getFollowersData().observe(this) {
            listFollowers.addAll(it)
        }

        detailViewModel.getFollowing(username)
        detailViewModel.getFollowingData().observe(this) {
            listFollowing.addAll(it)
        }

        setPagerAdapter()
    }

    private fun setLoadingData() {
        binding.progressBar.visibility = View.GONE
        binding.container.visibility = View.VISIBLE
    }

    private fun setUsersData(users: DetailUser) {
        setView(binding.tvName, users.name)
        setView(binding.tvUsername, users.username)
        setView(binding.tvUrl, users.url)
        setView(binding.tvCompany, users.company)
        setImage(binding.ivAvatar, users.avatar)

        binding.btnFavorite.setOnClickListener {
            val followers = ListFollowersUsers(listFollowers)
            val following = ListFollowingUsers(listFollowing)
            val userRoom =
                DetailUserDB(users.name, users.username, users.avatar, users.url, users.company, followers, following)
            detailViewModel.insertData(userRoom)
            Toast.makeText(this, getString(R.string.notification_favorite), Toast.LENGTH_SHORT).show()
        }
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
        val sectionsPagerAdapter = SectionsPagerAdapter(this, username)
        val viewPager = binding.viewPager
        val tabs = binding.tabLayout

        binding.viewPager.adapter = sectionsPagerAdapter
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