package com.ibrahimofick.github.DATABASE

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DaoFavorite {
    @Insert
    fun insert(favoriteUser: FavoriteUser)

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id = :id")
    fun check(id: Int): Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id")
    fun delete(id: Int): Int

    @Query("SELECT * FROM favorite_user")
    fun getAllNotes(): LiveData<List<FavoriteUser>>
}