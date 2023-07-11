package com.axuca.app.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.axuca.app.R
import com.axuca.app.databinding.FragmentHomeBinding
import com.axuca.app.util.addCarouselEffect
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val viewModel by viewModels<HomeViewModel>()

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
        viewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { state -> onStateChanged(state) }
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

    private fun onStateChanged(state: HomeViewModel.HomeState?) {
        when (state) {
            is HomeViewModel.HomeState.AllProducts -> {
                Timber.d("Result")
                Snackbar.make(
                    binding.root,
                    "Returned list item count : ${state.data.size}",
                    Snackbar.LENGTH_SHORT
                ).show()
                binding.progressBar.visibility = View.GONE
            }

            is HomeViewModel.HomeState.Loading -> {
                Timber.d("Loading")
                binding.progressBar.visibility = View.VISIBLE
            }

            is HomeViewModel.HomeState.Error-> {
                Timber.d("Error : ${state.throwable.printStackTrace()}")
                binding.progressBar.setBackgroundColor(Color.RED)
            }
            else -> {
                Timber.d("Else")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}