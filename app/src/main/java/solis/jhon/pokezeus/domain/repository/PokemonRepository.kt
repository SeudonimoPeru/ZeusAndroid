package solis.jhon.pokezeus.domain.repository

import kotlinx.coroutines.flow.Flow
import solis.jhon.pokezeus.data.network.model.PokemonDetailResponse
import solis.jhon.pokezeus.data.network.model.PokemonListResponse

interface PokemonRepository {

    suspend fun pokemonList(): Flow<PokemonListResponse>

    suspend fun pokemonList(offset: String): Flow<PokemonListResponse>

    suspend fun savePokemonList(data: PokemonListResponse)

    suspend fun getPokemonList(limit: Int, offset: Int): Flow<PokemonListResponse>

    suspend fun pokemonDetail(name: String): Flow<PokemonDetailResponse>

    suspend fun savePokemonDetail(data: PokemonDetailResponse)

    suspend fun getPokemonDetail(name: String): Flow<PokemonDetailResponse>

}