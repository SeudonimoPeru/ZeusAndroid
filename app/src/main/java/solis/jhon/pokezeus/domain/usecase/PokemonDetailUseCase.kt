package solis.jhon.pokezeus.domain.usecase

import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import solis.jhon.pokezeus.domain.mapper.toDomain
import solis.jhon.pokezeus.domain.model.PokemonDetailModel
import solis.jhon.pokezeus.domain.repository.PokemonRepository
import solis.jhon.pokezeus.domain.utils.ResultType
import solis.jhon.pokezeus.domain.utils.isInternetAvailable
import javax.inject.Inject

class PokemonDetailUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val context: Context
) {

    suspend fun invoke(name: String): Flow<ResultType<PokemonDetailModel>> = flow {
        emit(ResultType.Loading)
        try {
            if (isInternetAvailable(context)) {
                Log.i("**** isInternetAvailable: ", "true --------------")
                pokemonRepository.pokemonDetail(name).collect { data ->
                    Log.i("**** ResultType.Success: ", "Antes de guardar *********************")
                    pokemonRepository.savePokemonDetail(data)
                    Log.i("**** ResultType.Success: ", "Despues de guardar *********************")
                    emit(ResultType.Success(data.toDomain()))
                }
            } else {
                Log.i("**** isInternetAvailable: ", "false --------------")
                val dataFromDb = pokemonRepository.getPokemonDetail(name)
                dataFromDb.collect {
                    emit(ResultType.Success(it.toDomain()))
                }
            }
        } catch (e: Exception) {
            emit(ResultType.Error(e))
        }
    }.catch { e -> emit(ResultType.Error(e as Exception)) }


}