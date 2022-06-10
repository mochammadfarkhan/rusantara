package com.capstone.rusantara.activity.main.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.rusantara.activity.login.LoginActivity
import com.capstone.rusantara.databinding.FragmentProfileBinding
import com.capstone.rusantara.utils.Constant
import com.capstone.rusantara.utils.PreferencesHelper
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var preferencesHelper: PreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        preferencesHelper = PreferencesHelper(requireContext())
        binding.profEmailValue.text = preferencesHelper.getString(Constant.PREF_EMAIL)
        
        binding.buttonLogout.setOnClickListener{
            logout()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun logout() {
        auth.signOut()
        preferencesHelper.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}