package com.capstone.rusantara.activity.main.ui.home

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.rusantara.activity.upload.UploadImageActivity
import com.capstone.rusantara.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.tvDescription
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playAnimation()

        binding.takePhotoButton.setOnClickListener{
            val intent = Intent(requireContext(), UploadImageActivity::class.java)
            startActivity(intent)
        }
    }

    private fun playAnimation() {

        ObjectAnimator.ofFloat(binding.tvRusantaraSupport, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

//        val rusantaraTextView =
//            ObjectAnimator.ofFloat(binding.tvRusantara, View.ALPHA, 1f).setDuration(500)
//        val descriptionTextView =
//            ObjectAnimator.ofFloat(binding.tvDescription, View.ALPHA, 1f).setDuration(500)
//        val takePhotoButton = ObjectAnimator.ofFloat(binding.takePhotoButton, View.ALPHA, 1f).setDuration(500)
//
//        AnimatorSet().apply {
//            playSequentially(rusantaraTextView, descriptionTextView, takePhotoButton)
//            startDelay = 500
//        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}