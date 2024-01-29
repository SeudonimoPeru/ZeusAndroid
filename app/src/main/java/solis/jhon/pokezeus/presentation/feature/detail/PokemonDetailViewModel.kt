package solis.jhon.pokezeus.presentation.feature.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import solis.jhon.pokezeus.domain.model.PokemonDetailModel
import solis.jhon.pokezeus.domain.usecase.PokemonDetailUseCase
import solis.jhon.pokezeus.domain.utils.ResultType
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val useCase: PokemonDetailUseCase) : ViewModel() {

    var pokemonDetail = MutableLiveData<PokemonDetailModel?>()
    var name = MutableLiveData("")
    var message = MutableLiveData("")
    var loading = MutableLiveData(false)

    fun loadData() {
        Log.i("**** loadData: ", name.value.toString())
        viewModelScope.launch {
            Log.i("**** viewModelScope: ","*********")
            useCase.invoke(name.value.toString()).collect {
                Log.i("**** useCase.invoke: ","*********")
                when (it) {
                    is ResultType.Loading -> {
                        Log.i("**** it.data: ", "Loading.......")
                        loading.value = true
                    }
                    is ResultType.Success -> {
                        Log.i("**** it.data: ", it.data.toString())
                        loading.value = false
                        pokemonDetail.value = it.data
                    }
                    is ResultType.Error -> {
                        val error = it.exception
                        loading.value = false
                        message.value = error.message.toString()
                        Log.i("**** error: ", error.message.toString())
                    }
                }
            }
        }
    }
}