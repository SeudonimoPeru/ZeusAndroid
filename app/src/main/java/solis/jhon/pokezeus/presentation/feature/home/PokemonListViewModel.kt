package solis.jhon.pokezeus.presentation.feature.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import solis.jhon.pokezeus.domain.model.PokemonModel
import solis.jhon.pokezeus.domain.usecase.PokemonUseCase
import solis.jhon.pokezeus.domain.utils.ResultType
import solis.jhon.pokezeus.presentation.feature.home.adapter.PokemonAdapter
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(private val useCase: PokemonUseCase) : ViewModel() {

    var pokemonList = mutableListOf<PokemonModel>()
    var nextPage = MutableLiveData("")
    var message = MutableLiveData("")
    var loading = MutableLiveData(false)
    //val _adapterPokemon = PokemonAdapter(callback = ::zuperPuntosUsarCallback)
    var _adapterPokemon: PokemonAdapter? = null

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            useCase.invoke(nextPage.value.toString()).collect {
                when (it) {
                    is ResultType.Loading -> {
                        loading.value = true
                    }
                    is ResultType.Success -> {
                        loading.value = false
                        if ((nextPage.value ?: "").isEmpty()) {
                            pokemonList = it.data.results?.toMutableList() ?: arrayListOf()
                            _adapterPokemon?.bindItems(pokemonList)
                        } else {
                            var list = mutableListOf <PokemonModel>()
                            it.data.results?.forEach {
                                pokemonList.add(it)
                                list.add(it)
                            }
                            _adapterPokemon?.bindNewItems(list)
                        }
                        nextPage.value = it.data.next
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