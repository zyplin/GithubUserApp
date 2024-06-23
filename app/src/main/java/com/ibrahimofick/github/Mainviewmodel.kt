package com.ibrahimofick.github

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ibrahimofick.github.API.api
import com.ibrahimofick.github.DATA.response
import com.ibrahimofick.github.DATA.user
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Mainviewmodel: ViewModel() {

    companion object {
        private const val TAG = "MainActivity"
    }

    val listUsers = MutableLiveData<List<user>>()

    fun SearchUsers(query: String) {
        api.getapiservice().getSearchUsers(query).enqueue(object : Callback<response> {
            override fun onResponse(
                call: Call<response>, response: Response<response>
            ) {
                if (response.isSuccessful) {
                    listUsers.postValue(response.body()?.items)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<response>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getSearch(): LiveData<List<user>> {
        return listUsers
    }
}