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
import solis.jhon.pokezeus.domain.mapper.toDomain
import solis.jhon.pokezeus.domain.model.PokemonDetailModel
import solis.jhon.pokezeus.domain.model.PokemonModel
import solis.jhon.pokezeus.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonService: PokemonService,
    private val pokemonDao: PokemonDao
): PokemonRepository {


    override fun pokemonList(limit: Int, offset: Int): Flow<PokemonListResponse> = flow {
        emit(pokemonService.pokemonList(limit, offset))
    }.flowOn(Dispatchers.IO)

    override fun pokemonListOffline(limit: Int, offset: Int): Flow<List<PokemonModel>> {
        return pokemonDao.getPokemon(limit, offset)
            .map { pokemonList ->
                pokemonList.map { it.toDomain() }
            }.flowOn(Dispatchers.IO)
    }

    override fun pokemonDetail(name: String): Flow<PokemonDetailResponse> = flow {
        emit(pokemonService.pokemonDetail(name))
    }.flowOn(Dispatchers.IO)

    override fun pokemonDetailOffline(name: String): Flow<PokemonDetailModel> {
        return pokemonDao.getPokemonDetail(name)
            .map { pokemon ->
                pokemon.toDomain()
            }.flowOn(Dispatchers.IO)
    }

}