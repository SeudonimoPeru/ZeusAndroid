package solis.jhon.pokezeus.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import solis.jhon.pokezeus.domain.usecase.PokemonDetailUseCase
import solis.jhon.pokezeus.domain.usecase.PokemonUseCase
import solis.jhon.pokezeus.presentation.feature.detail.PokemonDetailViewModel
import javax.inject.Inject

class PokemonDetailViewModelFactory @Inject constructor(private val pokemonUseCase: PokemonUseCase, private val pokemonDetailUseCase: PokemonDetailUseCase) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonDetailViewModel::class.java)) {
            return PokemonDetailViewModel(pokemonUseCase, pokemonDetailUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}