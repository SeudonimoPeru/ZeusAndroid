package solis.jhon.pokezeus.presentation.feature.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import solis.jhon.pokezeus.domain.model.PokemonDetailModel
import solis.jhon.pokezeus.domain.model.PokemonModel
import solis.jhon.pokezeus.domain.usecase.PokemonDetailUseCase
import solis.jhon.pokezeus.domain.usecase.PokemonUseCase
import solis.jhon.pokezeus.domain.utils.ResultType
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val pokemonUseCase: PokemonUseCase, private val pokemonDetailUseCase: PokemonDetailUseCase) : ViewModel() {

    var pokemonDetail = MutableLiveData<PokemonDetailModel?>()
    var name = MutableLiveData("")
    var message = MutableLiveData("")
    var loading = MutableLiveData(false)

    fun loadData() {
        viewModelScope.launch {
            pokemonDetailUseCase.invoke(name.value.toString()).collect {
                when (it) {
                    is ResultType.Loading -> {
                        loading.value = true
                    }
                    is ResultType.Success -> {
                        loading.value = false
                        pokemonDetail.value = it.data
                    }
                    is ResultType.Error -> {
                        val error = it.exception
                        loading.value = false
                        message.value = error.message.toString()
                    }
                }
            }
        }
    }

    fun updatePokemon(pokemon: PokemonModel) {
        viewModelScope.launch {
            pokemonUseCase.update(pokemon).collect {
                when (it) {
                    is ResultType.Loading -> {
                        loading.value = true
                    }
                    is ResultType.Success -> {
                        loading.value = false
                    }
                    is ResultType.Error -> {
                        val error = it.exception
                        loading.value = false
                        message.value = error.message.toString()
                    }
                }
            }
        }
    }
}