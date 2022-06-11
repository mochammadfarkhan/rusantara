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
import com.capstone.rusantara.utils.Constant.Companion.PREF_EMAIL
import com.capstone.rusantara.utils.PreferencesHelper
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            val email = binding.idEmail.text.toString().trim()
            val password = binding.idPassword.text.toString().trim()
            when {
                email.isEmpty() -> {
                    binding.tvEmail.error = getString(R.string.error_email)
                }
                password.isEmpty() -> {
                    binding.tvPassword.error = getString(R.string.error_password)
                }
                else -> {
                    binding.progressBar.visibility = View.VISIBLE
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) {
                            if (it.isSuccessful) {
                                binding.progressBar.visibility = View.GONE
                                Intent(
                                    this@LoginActivity,
                                    MenuActivity::class.java
                                ).also { intent ->
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                    Toast.makeText(this@LoginActivity, "Welcome ${auth.currentUser?.displayName}", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }
            }
        }
    }

    private fun playAnimation() {
        val logoImage = ObjectAnimator.ofFloat(binding.logo, View.ALPHA, 1f).setDuration(300)
        val loginTextView = ObjectAnimator.ofFloat(binding.login, View.ALPHA, 1f).setDuration(300)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(300)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(300)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(300)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(300)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(300)
        val register = ObjectAnimator.ofFloat(binding.toRegister, View.ALPHA, 1f).setDuration(300)

        AnimatorSet().apply {
            playSequentially(
                logoImage,
                loginTextView,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login,
                register
            )
            startDelay = 400
        }.start()
    }
}