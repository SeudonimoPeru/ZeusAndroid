package solis.jhon.pokezeus.domain.repository

import kotlinx.coroutines.flow.Flow
import solis.jhon.pokezeus.data.network.model.PokemonListResponse

interface PokemonRepository {

    suspend fun pokemonList(): Flow<PokemonListResponse>

    suspend fun pokemonList(nextPage: String): Flow<PokemonListResponse>

    suspend fun savePokemonList(data: PokemonListResponse)

    suspend fun getPokemonList(limit: Int, offset: Int): Flow<PokemonListResponse>

}