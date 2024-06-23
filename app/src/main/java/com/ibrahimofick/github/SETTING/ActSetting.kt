package com.ibrahimofick.github.SETTING


import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.ibrahimofick.github.R
import com.google.android.material.switchmaterial.SwitchMaterial

private val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "settings")


class ActSetting : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportActionBar?.apply {
            title = getString(R.string.nav_settings)
            setDisplayHomeAsUpEnabled(true)
        }

        val switchTheme = findViewById<SwitchMaterial>(R.id.sm_theme)

        val pref = PreferencesSetting.getInstance(dataStore)
        val viewModelSettings =
            ViewModelProvider(this, Factviewmodel(pref)).get(Settingviewmodel::class.java)

        viewModelSettings.getTheme().observe(this) { isDarkModeActive: Boolean ->
            switchTheme.isChecked = isDarkModeActive
            updateTheme(isDarkModeActive)
        }

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModelSettings.saveTheme(isChecked)
        }
    }

    private fun updateTheme(isDarkModeActive: Boolean) {
        val mode = when (isDarkModeActive) {
            true -> AppCompatDelegate.MODE_NIGHT_YES
            false -> AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}