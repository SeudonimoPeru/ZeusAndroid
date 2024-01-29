package solis.jhon.pokezeus.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import solis.jhon.pokezeus.domain.usecase.PokemonUseCase
import solis.jhon.pokezeus.presentation.feature.home.PokemonListViewModel
import javax.inject.Inject

class PokemonViewModelFactory @Inject constructor(private val useCase: PokemonUseCase) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonListViewModel::class.java)) {
            return PokemonListViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}