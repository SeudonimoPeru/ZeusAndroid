package solis.jhon.pokezeus.domain.usecase

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import solis.jhon.pokezeus.domain.mapper.toDomain
import solis.jhon.pokezeus.domain.model.PokemonDetailModel
import solis.jhon.pokezeus.domain.model.PokemonModel
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
                pokemonRepository.pokemonDetail(name).collect { data ->
                    pokemonRepository.savePokemonDetail(data).collect {
                        emit(ResultType.Success(it.toDomain()))
                    }
                }
            } else {
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