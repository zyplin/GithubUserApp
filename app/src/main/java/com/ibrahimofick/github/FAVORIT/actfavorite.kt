package com.ibrahimofick.github.FAVORIT

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibrahimofick.github.DATABASE.FavoriteUser
import com.ibrahimofick.github.DATA.user
import com.ibrahimofick.github.UI.userdetailactivity
import com.ibrahimofick.github.R
import com.ibrahimofick.github.SETTING.ActSetting
import com.ibrahimofick.github.useradapter
import com.ibrahimofick.github.databinding.ActivityFavoriteBinding


class actfavorite : AppCompatActivity() {

    lateinit var binding: ActivityFavoriteBinding
    lateinit var adapter: useradapter
    lateinit var viewModel: favoriteviewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.nav_favorite)
            setDisplayHomeAsUpEnabled(true)
        }

        initAdapter()
        initViewModel()
        initRecyclerView()
        observeFavoriteUser()
    }

    private fun initAdapter() {
        adapter = useradapter().apply {
            setOnItemClickCallback(object : useradapter.OnItemClickCallback {
                override fun onItemClicked(data: user) {
                    navigateToUserDetail(data)
                }
            })
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(favoriteviewmodel::class.java)
    }

    private fun initRecyclerView() {
        binding.rvUser.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@actfavorite)
            adapter = this@actfavorite.adapter
        }
    }

    private fun observeFavoriteUser() {
        viewModel.FavoriteUser()?.observe(this) { users ->
            users?.let { userList ->
                val list = mapList(userList)
                adapter.setList(list)
            }
        }
    }

    private fun navigateToUserDetail(user: user) {
        Intent(this@actfavorite, userdetailactivity::class.java).apply {
            putExtra(userdetailactivity.USERNAME, user.login)
            putExtra(userdetailactivity.ID, user.id)
            putExtra(userdetailactivity.URL, user.avatar_url)
            startActivity(this)
        }
    }

    fun FavoriteUser.toUser(): user {
        return user(login, id, avatar_url)
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<user> {
        val listUsers = ArrayList<user>()
        for (user in users) {
            listUsers.add(user.toUser())
        }
        return listUsers
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.setting_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                startActivity(Intent(this, ActSetting::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}