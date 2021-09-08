package com.example.bfaa_submission_1

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bfaa_submission_1.databinding.FragmentFollowersBinding
import com.example.bfaa_submission_1.databinding.FragmentForagmentFollowingBinding

class ForagmentFollowing : Fragment() {

    private lateinit var binding: FragmentForagmentFollowingBinding
    private lateinit var adapter: UserAdapter
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentForagmentFollowingBinding.inflate(inflater, container, false)
        binding.listFollowing.layoutManager = LinearLayoutManager(activity)
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        binding.listFollowing.adapter = adapter
        return binding.root
    }

    companion object {
        private val ARG_USERNAME = "username"
        @JvmStatic
        fun newInstance(param1: String) =
            ForagmentFollowing().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME, param1)

                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val  user = arguments?.getString(ARG_USERNAME)

        profileViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ProfileViewModel::class.java)
        if (user != null) {
            profileViewModel.setFollowing(user)
        }

        profileViewModel.getFollowing().observe(viewLifecycleOwner) {
            adapter.setData(it)
            showLoading(false)
        }

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val followingIntent = Intent(activity, ProfileActivity::class.java)
                followingIntent.putExtra(ProfileActivity.EXTRA_USER, data)
                startActivity(followingIntent)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}