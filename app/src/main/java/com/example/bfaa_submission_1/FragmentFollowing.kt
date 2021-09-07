package com.example.bfaa_submission_1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bfaa_submission_1.databinding.FragmentFollowerBinding
import com.example.bfaa_submission_1.databinding.FragmentFollowingBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentFollowing.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentFollowing : Fragment() {
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var adapter: UserAdapter
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        binding.listFollowing.layoutManager = LinearLayoutManager(activity)
        adapter = UserAdapter()
        binding.listFollowing.adapter = adapter
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

        profileViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ProfileViewModel::class.java)
        if (user != null) {
            profileViewModel.setFollowing(user)
        }

        profileViewModel.getFollowing().observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

    }
}