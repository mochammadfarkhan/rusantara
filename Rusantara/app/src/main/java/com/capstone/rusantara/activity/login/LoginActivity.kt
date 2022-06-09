package com.capstone.rusantara.activity.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.capstone.rusantara.R
import com.capstone.rusantara.activity.main.MenuActivity
import com.capstone.rusantara.activity.register.RegisterActivity
import com.capstone.rusantara.databinding.ActivityLoginBinding
import com.capstone.rusantara.utils.PreferencesHelper
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var preferencesHelper: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferencesHelper = PreferencesHelper(this)

        setupView()
        setupAction()
        playAnimation()
    }

    override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
        val firebaseUser = auth.currentUser
        if (firebaseUser != null) {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }
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

        binding.toRegister.setOnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.idEmail.text.toString().trim()
            val password = binding.idPassword.text.toString().trim()
            when {
                email.isEmpty() -> {
                    binding.tvEmail.error = R.string.error_email.toString()
                }
                password.isEmpty() -> {
                    binding.tvPassword.error = R.string.error_password.toString()
                }
                else -> {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this){
                            if (it.isSuccessful){
                                Intent(this@LoginActivity, MenuActivity::class.java).also { intent ->
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                    //val firebaseUser = auth.currentUser
                                }
                            } else {
                                Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.logo, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(500)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding.toRegister, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(emailTextView, emailEditTextLayout, passwordTextView, passwordEditTextLayout, login, register)
            startDelay = 500
        }.start()
    }
}