package com.axuca.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.axuca.app.R
import com.axuca.app.databinding.FragmentHomeBinding
import com.axuca.app.util.addCarouselEffect
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val homeViewModel by viewModels<HomeViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            homeViewModel.state.collect { state ->
                when(state){
                    is HomeViewModel.HomeState.AllProducts -> {
                        Snackbar.make(binding.root, state.data.size.toString(), Snackbar.LENGTH_SHORT).show()
                    }
                    else -> {
                        Snackbar.make(binding.root, "Else block", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }

        val images = listOf(
            R.color.black,
            android.R.color.holo_blue_bright,
            android.R.color.holo_purple,
            android.R.color.holo_green_dark,
            android.R.color.holo_red_dark,
        )

        val adapter = ViewPagerImageAdapter(images)
        binding.viewpager.adapter = adapter
        binding.viewpager.addCarouselEffect()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}