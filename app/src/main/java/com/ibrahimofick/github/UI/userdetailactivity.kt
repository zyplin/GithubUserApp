package com.ibrahimofick.github.UI

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ibrahimofick.github.R
import com.ibrahimofick.github.SETTING.ActSetting
import com.ibrahimofick.github.databinding.ActivityUserDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class userdetailactivity : AppCompatActivity() {

    private val binding by lazy { ActivityUserDetailBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this).get(userviewmodel::class.java) }
    private var DetailChecking = false

    companion object {
        var USERNAME = "username"
        var ID = "id"
        var URL = "url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.nav_overview)
            setDisplayHomeAsUpEnabled(true)
        }

        val username = intent.getStringExtra(USERNAME)!!
        val id = intent.getIntExtra(ID, 0)
        val avatarUrl = intent.getStringExtra(URL)

        val bundle = Bundle().apply {
            putString(USERNAME, username)
        }

        viewModel.DetailUser(username)
        showLoadingUserDetail(true)

        viewModel.getUser().observe(this) { user ->
            when (user) {
                null -> showLoadingUserDetail(true)
                else -> {
                    binding.apply {
                        with(user) {
                            namenamo.text = name
                            usernamenamo.text = login

                            Glide.with(this@userdetailactivity)
                                .load(avatar_url)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .circleCrop().into(profilenamo)

                            locationnamo.text = location
                            followersnamo.text = followers.toString()
                            followingnamo.text = following.toString()
                        }
                    }
                    showLoadingUserDetail(false)
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.detail(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    DetailChecking = count > 0
                    binding.toggleFavorite.isChecked = DetailChecking
                }
            }
        }

        binding.toggleFavorite.setOnClickListener {
            DetailChecking = !DetailChecking
            when(DetailChecking) {
                true -> viewModel.addFavorite(username, id, avatarUrl!!)
                false -> viewModel.removeFavorite(id)
            }
        }

        val sectionPagerAdapter = sectionAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }

    private fun showLoadingUserDetail(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.setting_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                startActivity(Intent(this, ActSetting::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}