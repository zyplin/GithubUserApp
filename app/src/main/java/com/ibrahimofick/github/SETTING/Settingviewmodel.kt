package com.ibrahimofick.github.SETTING

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class Settingviewmodel(private val pref: PreferencesSetting) : ViewModel() {

    fun getTheme(): LiveData<Boolean> {
        return pref.ThemeSetting().asLiveData()
    }

    fun saveTheme(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveTheme(isDarkModeActive)
        }
    }
}