package solis.jhon.pokezeus.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import solis.jhon.pokezeus.data.network.model.PokemonDetailResponse
import solis.jhon.pokezeus.data.network.model.PokemonListResponse
import solis.jhon.pokezeus.domain.model.PokemonModel

interface PokemonRepository {

    suspend fun pokemonList(): Flow<PokemonListResponse>

    suspend fun pokemonList(offset: String): Flow<PokemonListResponse>

    suspend fun savePokemonList(data: PokemonListResponse): Flow<PokemonListResponse>

    suspend fun savePokemon(data: PokemonModel)

    suspend fun getPokemonList(limit: Int, offset: Int): Flow<PokemonListResponse>

    suspend fun pokemonDetail(name: String): Flow<PokemonDetailResponse>

    suspend fun savePokemonDetail(data: PokemonDetailResponse) : Flow<PokemonDetailResponse>

    suspend fun getPokemonDetail(name: String): Flow<PokemonDetailResponse>

}