package solis.jhon.pokezeus.presentation.feature.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dagger.hilt.android.AndroidEntryPoint
import solis.jhon.pokezeus.R
import solis.jhon.pokezeus.databinding.FragmentPokemonDetailBinding
import solis.jhon.pokezeus.domain.model.PokemonModel
import solis.jhon.pokezeus.domain.utils.applyOpacity
import solis.jhon.pokezeus.domain.utils.twoFirstLetter

@AndroidEntryPoint
class PokemonDetailFragment(
    private var pokemon: PokemonModel,
    val callback: () -> Unit,
    private val backgroundColorSelected: Int,
    private val initialsColorSelected: Int) : DialogFragment() {

    private val viewModel: PokemonDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogTheme)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        setupObservers(binding)
        setupViews(binding)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.START)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun setupObservers(binding: FragmentPokemonDetailBinding) {
        viewModel.name.value = pokemon.name
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.llLoading.visibility = View.VISIBLE
            } else {
                binding.llLoading.visibility = View.GONE
            }
        }

        viewModel.pokemonDetail.observe(viewLifecycleOwner) { pokemonDetail ->
            binding.apply {
                if (pokemonDetail?.imageDefault.isNullOrEmpty()) {
                    createImageWithInitials(pokemonDetail?.name, binding)
                } else {
                    tvInitialsPokemon.visibility = View.GONE
                    context?.let {
                        Glide.with(it)
                            .load(pokemonDetail?.imageDefault)
                            .placeholder(R.drawable.placeholder_default)
                            .listener(object : RequestListener<Drawable?> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Drawable?>,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    tvInitialsPokemon.visibility = View.VISIBLE
                                    createImageWithInitials(pokemonDetail?.name, binding)
                                    return false
                                }

                                override fun onResourceReady(
                                    resource: Drawable,
                                    model: Any,
                                    target: Target<Drawable?>?,
                                    dataSource: DataSource,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    return false
                                }
                            })
                            .into(ivPokemon)
                    }
                }
                tvName.text = pokemonDetail?.name
                tvHeight.text = pokemonDetail?.height.toString()
                tvWeight.text = pokemonDetail?.weight.toString()
                tvTypes.text = pokemonDetail?.types
                tbFavorite.isChecked = pokemon.favorite
            }
        }
        viewModel.loadData()
    }

    private fun setupViews(binding: FragmentPokemonDetailBinding) {
        var backgroundColor = context?.getColor(R.color.md_theme_primaryContainer)
        if(backgroundColorSelected != 0) {
            backgroundColor = backgroundColorSelected
        }
        backgroundColor?.let { binding.cvDetail.setBackgroundColor(it) }
        isCancelable = false
        binding.apply {
            ivClose.setOnClickListener {
                callback()
                dismiss()
            }
            tbFavorite.setOnCheckedChangeListener { buttonView, isChecked ->
                pokemon.favorite = isChecked
                viewModel.updatePokemon(pokemon)
            }
        }
    }

    fun createImageWithInitials(name: String?, binding: FragmentPokemonDetailBinding) {
        binding.apply {
            if (name.isNullOrEmpty()) {
                ivPokemon.setImageDrawable(context?.getDrawable(R.drawable.placeholder_default))
            } else {
                var colorInitials = context?.getColor(R.color.md_theme_primary)

                if (initialsColorSelected != 0) {
                    colorInitials = initialsColorSelected
                }

                ivPokemon.visibility = View.GONE
                tvInitialsPokemon.text = name.twoFirstLetter()
                colorInitials?.let {
                    tvInitialsPokemon.setTextColor(it)
                    cvPokemonImage.strokeColor = it
                    cvPokemonImage.setCardBackgroundColor(it.applyOpacity(0.4F))
                }
                cvPokemonImage.strokeWidth = 12
            }
        }
    }
}