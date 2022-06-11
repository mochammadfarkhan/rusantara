package com.capstone.rusantara.activity.main.ui.search

import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.rusantara.api.ApiConfig
import com.capstone.rusantara.api.ApiService
import com.capstone.rusantara.models.ImageData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private val _listFoods = MutableLiveData<ArrayList<ImageData>>()
    val listFoods: LiveData<ArrayList<ImageData>> = _listFoods

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isNotFound = MutableLiveData<Boolean>()
    val isNotFound: LiveData<Boolean> = _isNotFound

    fun searchFoods(query: String, idToken: String?) {
        _isLoading.postValue(true)

        val service = ApiConfig.getApiService().searchFoods(query,"Bearer $idToken")
        service.enqueue(object : Callback<ArrayList<ImageData>> {
            override fun onResponse(
                call: Call<ArrayList<ImageData>>,
                response: Response<ArrayList<ImageData>>
            ) {
                _isLoading.postValue(false)
                _isNotFound.postValue(true)

                if (response.isSuccessful) {
                    _isNotFound.postValue(false)

                    val responseBody = response.body()!!
                    _listFoods.postValue(responseBody)
                }
            }
            override fun onFailure(call: Call<ArrayList<ImageData>>, t: Throwable) {
                _isLoading.postValue(false)
            }
        })
    }

//    fun getAllFoods(idToken: String?) {
//        _isLoading.postValue(true)
//
//        val service = ApiConfig.getApiService().getAllFoods("Bearer $idToken")
//        service.enqueue(object : Callback<ArrayList<ImageData>> {
//            override fun onResponse(
//                call: Call<ArrayList<ImageData>>,
//                response: Response<ArrayList<ImageData>>
//            ) {
//                _isLoading.postValue(false)
//                _isNotFound.postValue(true)
//
//                if (response.isSuccessful) {
//                    _isNotFound.postValue(false)
//
//                    val responseBody = response.body()!!
//                    _listFoods.postValue(responseBody)
//                }
//            }
//            override fun onFailure(call: Call<ArrayList<ImageData>>, t: Throwable) {
//                _isLoading.postValue(false)
//            }
//        })
//    }
}