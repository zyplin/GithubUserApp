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

class ViewmodelFollowers : ViewModel() {

    companion object {
        private const val TAG = "FollowersViewModel"
    }

    private val _listFollowers = MutableLiveData<List<user>>()
    val listFollowers: LiveData<List<user>> get() = _listFollowers

    fun NamoFollowers(username: String) {
        api.getapiservice().getFollowers(username).enqueue(object : Callback<ArrayList<user>> {
            override fun onResponse(call: Call<ArrayList<user>>, response: Response<ArrayList<user>>) {
                _listFollowers.postValue(
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

    @JvmName("getListFollowersLiveData")
    fun getFollowers(): LiveData<List<user>> {
        return listFollowers
    }
}