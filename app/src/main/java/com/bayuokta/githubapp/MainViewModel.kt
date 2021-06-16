package com.bayuokta.githubapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bayuokta.githubapp.api.ApiConfig
import com.bayuokta.githubapp.api.ApiResponse
import com.bayuokta.githubapp.model.ItemsItem
import com.bayuokta.githubapp.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel:ViewModel() {
    fun getUser(query:String): LiveData<ApiResponse<List<ItemsItem?>?>>{
        val resultData = MutableLiveData<ApiResponse<List<ItemsItem?>?>>()

        val client = ApiConfig.getApiService().getUsers(query)
        client.enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful){
                    resultData.value = ApiResponse.Success(response.body()?.items)
                    if (!response.body()?.items.isNullOrEmpty()){
                        resultData.value = ApiResponse.Success(response.body()?.items)
                    }else{
                        resultData.value = ApiResponse.Empty
                    }
                }else{
                    resultData.value = ApiResponse.Empty
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
            }

        })
        return resultData
    }
}