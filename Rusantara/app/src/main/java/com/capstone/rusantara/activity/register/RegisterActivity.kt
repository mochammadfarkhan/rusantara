package com.capstone.rusantara.activity.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.capstone.rusantara.R
import com.capstone.rusantara.api.ApiConfig
import com.capstone.rusantara.databinding.ActivityRegisterBinding
import com.capstone.rusantara.models.SignupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val username = binding.idUsername.text.toString()
            val email = binding.idEmail.text.toString().trim()
            val password = binding.idPassword.text.toString().trim()

            when {
                username.isEmpty() -> {
                    binding.idUsername.error = R.string.error_username.toString()
                }
                email.isEmpty() -> {
                    binding.idEmail.error = R.string.error_email.toString()
                }
                password.isEmpty() -> {
                    binding.idPassword.error = R.string.error_password.toString()
                }
                else -> {
                    val service = ApiConfig.getApiService().signupUser(username, email, password)
                    service.enqueue(object : Callback<SignupResponse> {
                        override fun onResponse(
                            call: Call<SignupResponse>,
                            response: Response<SignupResponse>
                        ) {
                            if (response.isSuccessful) {
                                val responseBody = response.body()
                                if (responseBody != null) {
                                    AlertDialog.Builder(this@RegisterActivity).apply {
                                        setTitle("Sign Up")
                                        setMessage("Account succesfully created")
                                        setPositiveButton("OK") { _, _ ->
                                            finish()
                                        }
                                        create()
                                        show()
                                    }
                                }
                            } else {
                                Toast.makeText(this@RegisterActivity, "Sign up Failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                            Toast.makeText(this@RegisterActivity, t.message, Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
    }

//    private fun showLoading(isLoading: Boolean) {
//        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//    }

//    private fun registerValidation() {
//        registerViewModel.message.observe(this) {
//            if (it.error) {
//                Toast.makeText(
//                    this@RegisterActivity,
//                    registerViewModel.message.value?.message.toString(),
//                    Toast.LENGTH_SHORT
//                ).show()
//            } else {
//                Toast.makeText(
//                    this@RegisterActivity,
//                    registerViewModel.message.value?.message.toString(),
//                    Toast.LENGTH_SHORT
//                ).show()
//                finish()
//            }
//        }
//    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.logo, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.register, View.ALPHA, 1f).setDuration(500)
        val usernameTextView = ObjectAnimator.ofFloat(binding.usernameTextView, View.ALPHA, 1f).setDuration(500)
        val usernameEditTextLayout = ObjectAnimator.ofFloat(binding.tvUsername, View.ALPHA, 1f).setDuration(500)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(500)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(500)


        AnimatorSet().apply {
            playSequentially(
                title,
                usernameTextView,
                usernameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                signup
            )
            startDelay = 500
        }.start()
    }
}