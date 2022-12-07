package com.rbs.mygithubuser.ui.favorite.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rbs.mygithubuser.databinding.ActivityDetailFavoriteBinding
import com.rbs.mygithubuser.ui.favorite.FavoriteActivity.Companion.USERNAME
import com.rbs.mygithubuser.ui.favorite.detail.presenter.DetailFavoritePresenter
import com.rbs.mygithubuser.ui.favorite.detail.view.DetailFavoriteView

class DetailFavoriteActivity : AppCompatActivity(), DetailFavoriteView {

    private lateinit var binding: ActivityDetailFavoriteBinding
    private lateinit var presenter: DetailFavoritePresenter
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializePresenter()
    }

    @SuppressLint("SetTextI18n")
    override fun initializePresenter() {
        presenter = DetailFavoritePresenter(application)
        presenter.attachView(this)
        username = intent.getStringExtra(USERNAME).toString()
        presenter.getData(username).observe(this) {
            binding.tvName.text = it.fullname
            binding.tvUsername.text = username
            binding.tvFollowers.text = StringBuilder("${it.followers} followers")
            binding.tvRepository.text = StringBuilder("${it.repository} repository")

            if (it.company == null) {
                binding.tvCompany.text = it.company
            } else {
                binding.tvCompany.text = "Other"
            }

            title = StringBuilder("${it.fullname} Profile")
            Glide.with(this)
                .load(it?.userAvatar)
                .apply(RequestOptions().override(60, 60))
                .into(binding.ivPhoto)
        }
    }
}