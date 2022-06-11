package com.capstone.rusantara.activity.register

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.impl.utils.ContextUtil.getApplicationContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.rusantara.api.ApiConfig
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

//    private val _message = MutableLiveData<SignupResponse>()
//    val message: LiveData<SignupResponse> = _message
//
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading
//
//    private val _errorMessage = MutableLiveData<String>()
//    val errorMessage: LiveData<String> = _errorMessage
//
//    fun register(username: String, email: String, password: String) {
//        _isLoading.value = true
//
//        val gson = Gson()
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().signupUser(username, email, password)
//        client.enqueue(object : Callback<SignupResponse> {
//            override fun onResponse(
//                call: Call<SignupResponse>,
//                response: Response<SignupResponse>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    _message.value = response.body()
//                } else {
//                    val jsonError = JSONObject(response.errorBody()?.string() ?: "")
//                    val errorBody =
//                        gson.fromJson(jsonError.toString(), SignupResponse::class.java)
//                    _message.value = errorBody
//                }
//            }
//            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
//                _isLoading.value = false
//                _errorMessage.value = t.message
//                Log.e(TAG, "onFailure: ${t.message.toString()}")
//            }
//        })
//    }
//
//    companion object {
//        private const val TAG = "RegisterViewModel"
//    }
}