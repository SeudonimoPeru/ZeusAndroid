package solis.jhon.pokezeus.presentation.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import solis.jhon.pokezeus.domain.model.PokemonListModel
import solis.jhon.pokezeus.domain.usecase.PokemonUseCase
import solis.jhon.pokezeus.domain.utils.ResultType
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(private val useCase: PokemonUseCase) : ViewModel() {

    private val _dataState = MutableLiveData<ResultType<PokemonListModel>>()
    val dataState: LiveData<ResultType<PokemonListModel>> get() = _dataState

    fun loadData() {
        viewModelScope.launch {
            useCase.invoke("").collect {
                _dataState.value = it
            }
        }
    }
}