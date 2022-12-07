package com.rbs.mygithubuser.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.rbs.mygithubuser.R
import com.rbs.mygithubuser.data.detail.DetailUser
import com.rbs.mygithubuser.databinding.ActivityDetailBinding
import com.rbs.mygithubuser.db.User
import com.rbs.mygithubuser.ui.detail.adapter.SectionsPagerAdapter
import com.rbs.mygithubuser.ui.detail.presenter.DetailPresenter
import com.rbs.mygithubuser.ui.detail.view.DetailView
import com.rbs.mygithubuser.ui.favorite.FavoriteActivity
import com.rbs.mygithubuser.utils.RetrofitService

class DetailActivity : AppCompatActivity(), DetailView {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var presenter: DetailPresenter
    private lateinit var username: String
    private lateinit var avatar: String
    private lateinit var link: String
    private lateinit var fullname: String
    private lateinit var repository: String
    private lateinit var company: String
    private lateinit var followers: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializePresenter()
        getIntentData()
        setPagerAdapter()

        binding.fabFavorite.setOnClickListener {
            setDataIntoDatabase()
        }
    }

    private fun getIntentData() {
        username = intent.getStringExtra(USERNAME).toString()
        presenter.setUsername(username)
        presenter.getDetailUserData(username)
    }

    override fun initializePresenter() {
        presenter = DetailPresenter(application)
        presenter.attachView(this)
        presenter.initialization()
    }

    override fun setService() {
        val service = RetrofitService.getService()
        presenter.setService(service)
    }

    override fun setErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("SetTextI18n")
    override fun setData(user: DetailUser?) {
        avatar = user?.userAvatar.toString()
        link = user?.userGithubLink.toString()
        fullname = user?.userFullName.toString()
        followers = user?.userFollowers.toString()
        company = if(user?.userCompany != null) {
            user.userCompany.toString()
        } else {
            "Other"
        }

        repository = user?.userRepository.toString()

        binding.tvName.text = fullname
        binding.tvUsername.text = username
        binding.tvFollowers.text = StringBuilder("$followers followers")
        binding.tvRepository.text = StringBuilder("$repository repository")
        binding.tvCompany.text = company
        title = StringBuilder("$fullname Profile")

        Glide.with(this)
            .load(user?.userAvatar)
            .apply(RequestOptions().override(60, 60))
            .into(binding.ivPhoto)
    }

    private fun setDataIntoDatabase() {
        val user = User(username, avatar, link, fullname, repository, followers, company)
        presenter.insertData(user)
        startActivity(Intent(this, FavoriteActivity::class.java))
    }

    private fun setPagerAdapter() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, username)
        val viewPager = binding.viewPager
        val tabs = binding.tabs

        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    override fun stopLoading() {
        binding.progressLoading.visibility = View.GONE
        binding.container.visibility = View.VISIBLE
    }

    companion object {
        const val USERNAME = "username"

        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.text_follower, R.string.text_following)
    }
}