package com.rbs.mygithubuser.ui.detail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rbs.mygithubuser.data.search.SearchUserItems
import com.rbs.mygithubuser.databinding.FragmentFollowingBinding
import com.rbs.mygithubuser.ui.detail.adapter.FollowingAdapter
import com.rbs.mygithubuser.ui.detail.presenter.FollowingPresenter
import com.rbs.mygithubuser.ui.detail.view.FollowingView
import com.rbs.mygithubuser.utils.RetrofitService

class FollowingFragment : Fragment(), FollowingView {

    companion object {
        const val USERNAME = "username"

        fun newInstance(username: String): FollowingFragment {
            return FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(USERNAME, username)
                }
            }
        }
    }

    private lateinit var binding: FragmentFollowingBinding
    private lateinit var presenter: FollowingPresenter
    private lateinit var username: String
    private lateinit var adapter: FollowingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        username = arguments?.getString(USERNAME).toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializePresenter()
        initializationAdapter()
        getData()
    }

    private fun getData() {
        presenter.getUserData(username)
    }

    override fun initializePresenter() {
        presenter = FollowingPresenter()
        presenter.attachView(this)
        presenter.initialization()
    }

    override fun setService() {
        val service = RetrofitService.getService()
        presenter.setService(service)
    }

    private fun initializationAdapter() {
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.layoutManager = LinearLayoutManager(context)
    }

    override fun setErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun setData(user: List<SearchUserItems>?) {
        adapter = FollowingAdapter(user!!)
        binding.rvUsers.adapter = adapter
    }

    override fun stopLoading() {
        binding.progressLoading.visibility = View.GONE
        binding.rvUsers.visibility = View.VISIBLE
    }
}