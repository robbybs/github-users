package com.rbs.githubuser.ui.favorite.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.rbs.githubuser.databinding.ActivityDetailFavoriteBinding
import com.rbs.githubuser.db.DetailUserDB
import com.rbs.githubuser.ui.detail.DetailActivity

class DetailFavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailFavoriteViewModel by viewModels<DetailFavoriteViewModel> {
            DetailFavoriteViewModel.ViewModelFactory.getInstance(application)
            DetailFavoriteViewModel.ViewModelFactory(application)
        }

        val username = intent.getStringExtra(DetailActivity.USERNAME).toString()
        detailFavoriteViewModel.getData(username).observe(this) {
            setLoadingData()
            setUsersData(it)
        }
    }

    private fun setLoadingData() {
        binding.progressBar.visibility = View.GONE
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

    companion object {
        const val USERNAME = "username"
    }
}