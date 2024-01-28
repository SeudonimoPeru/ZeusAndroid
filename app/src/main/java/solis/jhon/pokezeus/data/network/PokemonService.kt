package solis.jhon.pokezeus.data.network

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path
import solis.jhon.pokezeus.data.network.model.PokemonDetailResponse
import solis.jhon.pokezeus.data.network.model.PokemonListResponse

interface PokemonService {

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2"
    }

    @GET("/pokemon")
    suspend fun pokemonList(
        @Query("limit") limit: Int = 25,
        @Query("offset") offset: Int = 0,
    ): Flow<PokemonListResponse>

    @GET("/{next}")
    suspend fun pokemonNextPage(
        @Path("next") nextPage: String
    ): Flow<PokemonListResponse>

    @GET("/pokemon/{name}")
    suspend fun pokemonDetail(@Path("name") name: String): PokemonDetailResponse
}