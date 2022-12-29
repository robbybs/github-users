package com.rbs.githubuser.ui.favorite.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rbs.githubuser.databinding.ActivityFavoriteBinding
import com.rbs.githubuser.db.DetailUserDB
import com.rbs.githubuser.ui.favorite.detail.DetailFavoriteActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter
    private lateinit var listUsers: List<DetailUserDB>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val favoriteViewModel by viewModels<FavoriteViewModel> {
            FavoriteViewModel.ViewModelFactory.getInstance(application)
            FavoriteViewModel.ViewModelFactory(application)
        }

        initializationAdapter()
        setData(favoriteViewModel)
    }

    private fun initializationAdapter() {
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        adapter = FavoriteAdapter()
        binding.rvUsers.adapter = adapter
    }

    private fun setData(favoriteViewModel: FavoriteViewModel) {
        favoriteViewModel.getAllUsers().observe(this) {
            if (it != null) {
                setLoadingData()
                listUsers = it
                adapter.setListUsers(it)
            }
        }

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DetailUserDB) {
                startActivity(
                    Intent(this@FavoriteActivity, DetailFavoriteActivity::class.java).putExtra(
                        DetailFavoriteActivity.USERNAME,
                        data.username
                    )
                )
            }
        })

        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    listUsers[viewHolder.adapterPosition].username?.let {
                        favoriteViewModel.deleteData(it)
                    }
                }
            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvUsers)
    }

    private fun setLoadingData() {
        binding.progressBar.visibility = View.GONE
        binding.rvUsers.visibility = View.VISIBLE
    }
}