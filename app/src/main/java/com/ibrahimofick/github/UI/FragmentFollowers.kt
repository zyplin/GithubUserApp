package com.ibrahimofick.github.UI

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibrahimofick.github.R
import com.ibrahimofick.github.useradapter
import com.ibrahimofick.github.databinding.FragmentFollowersBinding

class FragmentFollowers : Fragment(R.layout.fragment_followers) {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ViewmodelFollowers by viewModels()
    private lateinit var adapter: useradapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFollowersBinding.bind(view)
        setupRecyclerView()

        val args = requireArguments()
        username = args.getString(userdetailactivity.USERNAME).toString()

        viewModel.getFollowers().observe(viewLifecycleOwner) { followers ->
            followers?.let {
                val followersArrayList = ArrayList(it)
                adapter.setList(followersArrayList)
                showLoading(false)
            }
        }
        viewModel.NamoFollowers(username)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        adapter = useradapter()
        binding.rvUser.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = this@FragmentFollowers.adapter
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}