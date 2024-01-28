package solis.jhon.pokezeus.domain.repository

import kotlinx.coroutines.flow.Flow
import solis.jhon.pokezeus.data.network.model.PokemonDetailResponse
import solis.jhon.pokezeus.data.network.model.PokemonListResponse
import solis.jhon.pokezeus.domain.model.PokemonDetailModel
import solis.jhon.pokezeus.domain.model.PokemonModel

interface PokemonRepository {

    fun pokemonList(limit: Int, offset: Int): Flow<PokemonListResponse>

    fun pokemonListOffline(limit: Int, offset: Int): Flow<List<PokemonModel>>

    fun pokemonDetail(name: String): Flow<PokemonDetailResponse>

    fun pokemonDetailOffline(name: String): Flow<PokemonDetailModel>

}