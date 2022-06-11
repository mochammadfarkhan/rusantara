package com.capstone.rusantara.activity.detail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.rusantara.databinding.FragmentInfoBinding
import com.capstone.rusantara.models.ImageData

class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupData()
    }

    private fun setupData() {
        val imageData = getImageData()

        binding.description.text = imageData.info
        binding.specificLocation.text = imageData.location
    }

    private fun getImageData(): ImageData {
        return requireActivity().intent
            .getParcelableExtra<ImageData>(EXTRA_DATA) as ImageData
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}