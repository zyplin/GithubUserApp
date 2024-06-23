package com.ibrahimofick.github.UI

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ibrahimofick.github.API.api
import com.ibrahimofick.github.DATA.datauser
import com.ibrahimofick.github.DATABASE.DaoFavorite
import com.ibrahimofick.github.DATABASE.FavoriteUser
import com.ibrahimofick.github.DATABASE.Userbase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class userviewmodel(application: Application) : AndroidViewModel(application) {

    val user = MutableLiveData<datauser>()
    private val userDao: DaoFavorite? = Userbase.getDatabase(application)?.DaoFavorite()

    companion object {
        private const val TAG = "UserDetailActivity"
    }

    fun DetailUser(username: String) {
         api.getapiservice().getUserDetail(username).enqueue(object : Callback<datauser>{
            override fun onResponse(call: Call<datauser>, response: Response<datauser>) {
                when {
                    response.isSuccessful -> user.postValue(response.body())
                    else -> handleFailure("Failed to fetch user details: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<datauser>, t: Throwable) {
                handleFailure("Failed to fetch user details", t)
            }
        })
    }

    private fun handleFailure(message: String, throwable: Throwable? = null) {
        Log.e(TAG, message, throwable)
    }

    fun addFavorite(username: String, id: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(username, id, avatarUrl)
            userDao?.insert(user)
        }
    }

    fun detail(id: Int) = userDao?.check(id)

    fun getUser(): LiveData<datauser> {
        return user
    }

    fun removeFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.delete(id)
        }
    }
}