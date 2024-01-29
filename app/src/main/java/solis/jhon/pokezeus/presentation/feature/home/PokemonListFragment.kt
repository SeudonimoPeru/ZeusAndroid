package solis.jhon.pokezeus.presentation.feature.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import solis.jhon.pokezeus.databinding.FragmentPokemonListBinding
import solis.jhon.pokezeus.domain.utils.ResultType

@AndroidEntryPoint
class PokemonListFragment : Fragment() {

    private val viewModel: PokemonListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        setupObservers(binding)
        setupViews(binding)
        return binding.root
    }

    private fun setupObservers(binding: FragmentPokemonListBinding) {
        viewModel.dataState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultType.Loading -> {
                    binding.tvResult.text = "Loading..."
                }
                is ResultType.Success -> {
                    val data = result.data
                    binding.tvResult.text = "Data loaded: $data"
                }
                is ResultType.Error -> {
                    val error = result.exception
                    Log.e("Error: ", error.message.toString())
                    binding.tvResult.text = "Error: $error"
                }
            }
        }
    }

    private fun setupViews(binding: FragmentPokemonListBinding) {
        binding.btnLoadData.setOnClickListener {
            viewModel.loadData()
        }
    }
}