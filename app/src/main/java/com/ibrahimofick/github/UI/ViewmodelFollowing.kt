package com.ibrahimofick.github.UI


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ibrahimofick.github.API.api
import com.ibrahimofick.github.DATA.user
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewmodelFollowing : ViewModel() {

    companion object {
        private const val TAG = "Following"
    }

    private val _listFollowing = MutableLiveData<List<user>>()
    val listFollowing: LiveData<List<user>> = _listFollowing

    fun fetchFollowing(username: String) {
        api.getapiservice().getFollowing(username).enqueue(object : Callback<ArrayList<user>> {
            override fun onResponse(call: Call<ArrayList<user>>, response: Response<ArrayList<user>>) {
                _listFollowing.postValue(
                    when {
                        response.isSuccessful -> response.body()
                        else -> null
                    }
                )
            }

            override fun onFailure(call: Call<ArrayList<user>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}