package solis.jhon.pokezeus.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import solis.jhon.pokezeus.data.database.PokemonDao
import solis.jhon.pokezeus.data.network.PokemonService
import solis.jhon.pokezeus.data.network.model.PokemonListResponse
import solis.jhon.pokezeus.domain.mapper.asEntity
import solis.jhon.pokezeus.domain.mapper.toResponse
import solis.jhon.pokezeus.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonService: PokemonService,
    private val pokemonDao: PokemonDao
): PokemonRepository {

    override suspend fun pokemonList(): Flow<PokemonListResponse> = flow {
        emit(pokemonService.pokemonList())
    }.flowOn(Dispatchers.IO)

    override suspend fun pokemonList(nextPage: String): Flow<PokemonListResponse> = flow {
        emit(pokemonService.pokemonNextPage(nextPage))
    }.flowOn(Dispatchers.IO)

    override suspend fun savePokemonList(data: PokemonListResponse) {
        data.results?.let { list ->
            pokemonDao.savePokemons(list.map { it.asEntity() }) }
    }

    override suspend fun getPokemonList(limit: Int, offset: Int): PokemonListResponse {
        return PokemonListResponse(count = null, next = null, previous = null , results = pokemonDao.getPokemon(limit, offset)
            ?.map { it.toResponse() })
    }

}