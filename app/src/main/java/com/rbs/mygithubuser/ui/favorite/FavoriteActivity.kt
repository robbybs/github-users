package com.rbs.mygithubuser.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rbs.mygithubuser.databinding.ActivityFavoriteBinding
import com.rbs.mygithubuser.db.User
import com.rbs.mygithubuser.ui.favorite.detail.DetailFavoriteActivity
import com.rbs.mygithubuser.ui.favorite.presenter.FavoritePresenter
import com.rbs.mygithubuser.ui.favorite.view.FavoriteView

class FavoriteActivity : AppCompatActivity(), FavoriteView {

    companion object {
        const val USERNAME = "username"
    }

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var presenter: FavoritePresenter
    private lateinit var adapter: FavoriteAdapter
    private lateinit var user: List<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializationAdapter()
        initializePresenter()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initializePresenter() {
        presenter = FavoritePresenter(application)
        presenter.attachView(this)
        presenter.getData().observe(this) {
            user = it
            adapter = FavoriteAdapter(it)
            binding.rvUsers.adapter = adapter
            adapter.notifyDataSetChanged()
            setSwipeToDelete()
            adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
                override fun onItemClicked(data: User) {
                    val username = data.username
                    if (username != null) {
                        startActivity(
                            Intent(
                                this@FavoriteActivity,
                                DetailFavoriteActivity::class.java
                            ).putExtra(
                                USERNAME, username
                            )
                        )
                    }
                }
            })
        }
    }

    private fun initializationAdapter() {
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
    }

    private fun setSwipeToDelete() {
        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    presenter.deleteData(user[viewHolder.bindingAdapterPosition])
                }
            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvUsers)
    }
}