package com.ibrahimofick.github.SETTING


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class Factviewmodel(private val pref: PreferencesSetting) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(Settingviewmodel::class.java) -> {
                Settingviewmodel(pref) as T
            }
            else -> {
                throw IllegalArgumentException("Cannot create ViewModel for class: ${modelClass.name}")
            }
        }
    }
}