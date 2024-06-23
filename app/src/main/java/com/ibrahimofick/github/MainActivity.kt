package com.ibrahimofick.github

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibrahimofick.github.DATA.user
import com.ibrahimofick.github.FAVORIT.actfavorite
import com.ibrahimofick.github.UI.userdetailactivity
import com.ibrahimofick.github.SETTING.ActSetting
import com.ibrahimofick.github.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val viewModel: Mainviewmodel by lazy { ViewModelProvider(this).get(Mainviewmodel::class.java) }
    val adapter: useradapter by lazy { useradapter() }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupRecyclerView()

        binding.apply {
            btnSearch.setOnClickListener {
                searchUser()
            }

            textQuery.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        viewModel.getSearch().observe(this) { users ->
            users?.let {
                adapter.setList(ArrayList(users))
                showLoading(false)
            }
        }
    }

    private fun setupRecyclerView() {
        val onItemClickCallback = object : useradapter.OnItemClickCallback {
            override fun onItemClicked(data: user) {
                navigateToUserDetail(data)
            }
        }
        adapter.setOnItemClickCallback(onItemClickCallback)

        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = this@MainActivity.adapter
        }
    }

    fun user.createDetailIntent(context: Context): Intent {
        return Intent(context, userdetailactivity::class.java).apply {
            putExtra(userdetailactivity.USERNAME, login)
            putExtra(userdetailactivity.ID, id)
            putExtra(userdetailactivity.URL, avatar_url)
        }
    }

    private fun searchUser() {
        val query = binding.textQuery.text.toString()
        if (query.isEmpty()) return

        showLoading(true)
        viewModel.SearchUsers(query)
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.opsi_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                navigateToSettings()
                return true
            }

            R.id.favorite_menu -> {
                navigateToFavorite()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateToSettings() {
        Intent(this, ActSetting::class.java).also {
            startActivity(it)
        }
    }

    private fun navigateToUserDetail(user: user) {
        startActivity(user.createDetailIntent(this@MainActivity))
    }

    private fun navigateToFavorite() {
        Intent(this, actfavorite::class.java).also {
            startActivity(it)
        }
    }
}
