package com.ibrahimofick.github.FAVORIT

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ibrahimofick.github.DATABASE.Userbase
import com.ibrahimofick.github.DATABASE.DaoFavorite
import com.ibrahimofick.github.DATABASE.FavoriteUser

class favoriteviewmodel(application: Application) : AndroidViewModel(application) {
    private val userDao: DaoFavorite = Userbase.getDatabase(application).DaoFavorite()

    fun FavoriteUser(): LiveData<List<FavoriteUser>> {
        return userDao.getAllNotes()
    }
}