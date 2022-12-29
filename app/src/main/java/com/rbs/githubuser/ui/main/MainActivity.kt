package com.rbs.githubuser.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.rbs.githubuser.data.SearchUser
import com.rbs.githubuser.databinding.ActivityMainBinding
import com.rbs.githubuser.ui.detail.DetailActivity
import com.rbs.githubuser.ui.favorite.main.FavoriteActivity
import com.rbs.githubuser.utils.SettingPreference

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SearchUserAdapter
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreference.getInstance(dataStore)
        val searchUserViewModel by viewModels<SearchUserViewModel> {
            SearchUserViewModel.ViewModelFactory(pref)
        }

        initializationAdapter()
        setDarkMode(searchUserViewModel)
        setClickToggle(searchUserViewModel)
        setClickData(searchUserViewModel)
        setClickFavorite()
    }

    private fun initializationAdapter() {
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        adapter = SearchUserAdapter()
        binding.rvUsers.adapter = adapter
    }

    private fun setDarkMode(mainViewModel: SearchUserViewModel) {
        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }
    }

    private fun setClickToggle(mainViewModel: SearchUserViewModel) {
        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
                mainViewModel.saveThemeSetting(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
                mainViewModel.saveThemeSetting(false)
            }
        }
    }

    private fun setClickData(mainViewModel: SearchUserViewModel) {
        binding.btnSearch.setOnClickListener {
            val username = binding.inputSearch.editText?.text?.trim().toString()
            if (username.isNotEmpty()) {
                mainViewModel.searchUsers(username)
                binding.progressBar.visibility = View.VISIBLE
            }
        }

        mainViewModel.getUser().observe(this) {
            setLoadingData()
            adapter.setData(it)
            binding.rvUsers.scrollToPosition(0)
        }

        adapter.setOnItemClickCallback(object : SearchUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: SearchUser) {
                startActivity(
                    Intent(this@MainActivity, DetailActivity::class.java).putExtra(
                        DetailActivity.USERNAME,
                        data.username
                    )
                )
            }
        })
    }

    private fun setClickFavorite() {
        binding.btnFavorite.setOnClickListener {
            startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
        }
    }

    private fun setLoadingData() {
        binding.progressBar.visibility = View.GONE
        binding.rvUsers.visibility = View.VISIBLE
    }
}