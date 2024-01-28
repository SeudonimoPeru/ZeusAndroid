package solis.jhon.pokezeus.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import solis.jhon.pokezeus.data.database.PokemonDao
import solis.jhon.pokezeus.data.network.PokemonService
import solis.jhon.pokezeus.data.network.model.PokemonDetailResponse
import solis.jhon.pokezeus.data.network.model.PokemonListResponse
import solis.jhon.pokezeus.domain.mapper.asEntity
import solis.jhon.pokezeus.domain.mapper.toDomain
import solis.jhon.pokezeus.domain.mapper.toResponse
import solis.jhon.pokezeus.domain.model.PokemonDetailModel
import solis.jhon.pokezeus.domain.model.PokemonListModel
import solis.jhon.pokezeus.domain.model.PokemonModel
import solis.jhon.pokezeus.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonService: PokemonService,
    private val pokemonDao: PokemonDao
): PokemonRepository {

    override suspend fun pokemonList(): Flow<PokemonListResponse> {
        return flow { pokemonService.pokemonList() }
    }

    override suspend fun pokemonList(nextPage: String): Flow<PokemonListResponse> {
        return flow { pokemonService.pokemonNextPage(nextPage) }
    }

    override suspend fun savePokemonList(data: PokemonListResponse) {
        data.results?.let { list ->
            pokemonDao.savePokemons(list.map { it.asEntity() }) }
    }

    override suspend fun getPokemonList(limit: Int, offset: Int): PokemonListResponse {
        return PokemonListResponse(count = null, next = null, previous = null , results = pokemonDao.getPokemon(limit, offset)
            ?.map { it.toResponse() })
    }

    /*override fun pokemonNextPage(nextPage: String): Flow<PokemonListModel> = flow {
        emit(pokemonService.pokemonNextPage(nextPage))
    }.flowOn(Dispatchers.IO)

    override fun pokemonListOffline(limit: Int, offset: Int): Flow<List<PokemonModel>> {
        return pokemonDao.getPokemon(limit, offset)
            .map { pokemonList ->
                pokemonList.map { it.toDomain() }
            }.flowOn(Dispatchers.IO)
    }

    override fun pokemonDetail(name: String): Flow<PokemonDetailModel> = flow {*/
}