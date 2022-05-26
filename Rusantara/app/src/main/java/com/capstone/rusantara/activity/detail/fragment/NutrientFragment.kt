package com.capstone.rusantara.activity.detail.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.rusantara.databinding.FragmentNutrientBinding
import com.capstone.rusantara.models.ImageData

class NutrientFragment : Fragment() {

    private var _binding: FragmentNutrientBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNutrientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageData = requireActivity().intent
            .getParcelableExtra<ImageData>(InfoFragment.EXTRA_DATA) as ImageData

    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}