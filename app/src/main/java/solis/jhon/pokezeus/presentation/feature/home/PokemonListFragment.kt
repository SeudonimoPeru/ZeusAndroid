package solis.jhon.pokezeus.presentation.feature.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import solis.jhon.pokezeus.databinding.FragmentPokemonListBinding
import solis.jhon.pokezeus.presentation.feature.detail.PokemonDetailFragment
import solis.jhon.pokezeus.presentation.feature.home.adapter.PokemonAdapter

@AndroidEntryPoint
class PokemonListFragment : Fragment() {

    private val viewModel: PokemonListViewModel by viewModels()
    private var backgroundColorSelected = 0
    private var initialsColorSelected = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        setupValues()
        setupObservers(binding)
        setupViews(binding)
        return binding.root
    }

    private fun setupObservers(binding: FragmentPokemonListBinding) {
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.llLoading.visibility = View.VISIBLE
            } else {
                binding.llLoading.visibility = View.GONE
            }
        }
    }

    private fun setupViews(binding: FragmentPokemonListBinding) {
        binding.apply {
            rvPokemonList.apply {
                viewModel._adapterPokemon = PokemonAdapter(::showPokemonDetail, this.context, backgroundColorSelected, initialsColorSelected)
                this.adapter = viewModel._adapterPokemon
                this.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            }
            rvPokemonList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val visibleItemCount = rvPokemonList.layoutManager?.childCount ?: 0
                    val totalItemCount = rvPokemonList.layoutManager?.itemCount ?: 0
                    val firstVisibleItemPosition = (rvPokemonList.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= 25
                    ) {
                        viewModel.loadData()
                    }
                }

            })
        }
    }

    private fun setupValues() {
        arguments?.let {
            backgroundColorSelected = it.getInt("backgroundColor", 0)
            initialsColorSelected = it.getInt("initialsColor", 0)
        }
    }

    private fun showPokemonDetail(name: String) {
        Log.i("**** name: ", name)
        Log.i("**** backgroundColorSelected: ", backgroundColorSelected.toString())
        PokemonDetailFragment(name, backgroundColorSelected, initialsColorSelected).show(
            parentFragmentManager,
            "DialogPokemonDetail"
        )
    }
}