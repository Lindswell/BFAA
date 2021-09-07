package com.example.bfaa_submission_1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bfaa_submission_1.databinding.FragmentFollowerBinding


class FragmentFollower : Fragment() {

    private lateinit var binding: FragmentFollowerBinding
    private lateinit var adapter: UserAdapter
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        binding.listFollower.layoutManager = LinearLayoutManager(activity)
        adapter = UserAdapter()
        binding.listFollower.adapter = adapter
        return binding.root
    }

    companion object {
        private val ARG_GITHUB_USER = "username"
        fun newInstance(username: String) =
            FragmentFollower().apply {
                arguments = Bundle().apply {
                    putString(ARG_GITHUB_USER, username)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = arguments?.getString(ARG_GITHUB_USER)

        profileViewModel.setFollowers(user)
        profileViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ProfileViewModel::class.java)
        if (user != null) {
        }

        profileViewModel.getFollowers().observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

    }
}