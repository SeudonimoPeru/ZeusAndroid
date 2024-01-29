package solis.jhon.pokezeus.domain.usecase

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import solis.jhon.pokezeus.domain.mapper.toDomain
import solis.jhon.pokezeus.domain.model.PokemonListModel
import solis.jhon.pokezeus.domain.repository.PokemonRepository
import solis.jhon.pokezeus.domain.utils.ResultType
import solis.jhon.pokezeus.domain.utils.isInternetAvailable
import javax.inject.Inject

class PokemonUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val context: Context
) {

    suspend fun invoke(nextPage: String): Flow<ResultType<PokemonListModel>> = flow {
        emit(ResultType.Loading)
        try {
            if (isInternetAvailable(context)) {
                if (nextPage.isEmpty()) {
                    pokemonRepository.pokemonList().collect { data ->
                        pokemonRepository.savePokemonList(data)
                        emit(ResultType.Success(data.toDomain()))
                    }
                } else {
                    pokemonRepository.pokemonList(nextPage).collect { data ->
                        pokemonRepository.savePokemonList(data)
                        emit(ResultType.Success(data.toDomain()))
                    }
                }
            } else {
                val dataFromDb = pokemonRepository.getPokemonList(offset = nextPage.toInt(), limit = 25)
                emit(ResultType.Success(dataFromDb.toDomain()))
            }
        } catch (e: Exception) {
            emit(ResultType.Error(e))
        }
    }.catch { e -> emit(ResultType.Error(e as Exception)) }


}