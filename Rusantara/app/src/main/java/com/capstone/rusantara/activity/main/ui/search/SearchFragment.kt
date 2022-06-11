package com.capstone.rusantara.activity.main.ui.search

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.rusantara.activity.detail.DetailActivity
import com.capstone.rusantara.activity.detail.DetailActivity.Companion.EXTRA_DATA
import com.capstone.rusantara.adapter.ListFoodsAdapter
import com.capstone.rusantara.databinding.FragmentSearchBinding
import com.capstone.rusantara.models.ImageData
import com.google.firebase.auth.FirebaseAuth

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private var listFoods: ArrayList<ImageData> = ArrayList()
    private lateinit var auth: FirebaseAuth
    private lateinit var searchViewModel: SearchViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchViewModel =
            ViewModelProvider(this)[SearchViewModel::class.java]

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        setupViewModel()
        searchFoods()
//        getAllFoods()
    }

    private fun setupViewModel() {
        searchViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        }
        searchViewModel.isNotFound.observe(viewLifecycleOwner) {
            if (it) binding.textNotFound.visibility = View.VISIBLE
            else binding.textNotFound.visibility = View.GONE
        }
        searchViewModel.listFoods.observe(viewLifecycleOwner) {
            showRecyclerList(it)
        }
    }

    private fun showRecyclerList(listFoods: List<ImageData>) {
        if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvListSearch.layoutManager = GridLayoutManager(requireContext(), 2)
        } else {
            binding.rvListSearch.layoutManager = LinearLayoutManager(requireContext())
        }
        binding.rvListSearch.setHasFixedSize(true)
        val listFoodsAdapter = ListFoodsAdapter(listFoods)
        binding.rvListSearch.adapter = listFoodsAdapter

        listFoodsAdapter.setOnItemClickCallback(object : ListFoodsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ImageData) {
                val detailActivityIntent = Intent(requireContext(), DetailActivity::class.java)
                detailActivityIntent.putExtra(EXTRA_DATA, data)
                startActivity(detailActivityIntent)
            }
        })
    }

//    private fun getAllFoods() {
//        val firebaseUser = auth.currentUser
//        firebaseUser?.getIdToken(false)?.addOnSuccessListener { result ->
//            val idToken = result.token
//            searchViewModel.getAllFoods(idToken)
//        }
//    }

    private fun searchFoods() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    return true
                } else {
                    listFoods.clear()
                    showRecyclerList(listFoods)
                    getFoodsSearch(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun getFoodsSearch(query: String) {
        val firebaseUser = auth.currentUser
        firebaseUser?.getIdToken(false)?.addOnSuccessListener { result ->
            val idToken = result.token
            searchViewModel.searchFoods(query, idToken)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}