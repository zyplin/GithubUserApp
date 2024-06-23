package com.ibrahimofick.github.UI

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibrahimofick.github.R
import com.ibrahimofick.github.useradapter
import com.ibrahimofick.github.databinding.FragmentFollowingBinding

class FragmentFollowing : Fragment(R.layout.fragment_following) {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ViewmodelFollowing by viewModels()
    private lateinit var adapter: useradapter
    private lateinit var username: String

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFollowingBinding.bind(view)
        setupRecyclerView()

        val args = requireArguments()
        username = args.getString(userdetailactivity.USERNAME) ?: ""

        viewModel.listFollowing.observe(viewLifecycleOwner) { following ->
            following?.let {
                val followingArrayList = ArrayList(it)
                adapter.setList(followingArrayList)
                showLoading(false)
            }
        }
        viewModel.fetchFollowing(username)
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
            adapter = this@FragmentFollowing.adapter
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}
