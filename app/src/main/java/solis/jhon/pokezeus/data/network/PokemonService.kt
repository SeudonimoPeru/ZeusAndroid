package solis.jhon.pokezeus.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import solis.jhon.pokezeus.data.network.model.PokemonDetailResponse
import solis.jhon.pokezeus.data.network.model.PokemonListResponse

interface PokemonService {

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
        const val IMAGE_BASE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
    }

    @GET("pokemon")
    suspend fun pokemonList(
        @Query("limit") limit: Int = 25,
        @Query("offset") offset: Int = 0,
    ): PokemonListResponse

    @GET("pokemon/{name}")
    suspend fun pokemonDetail(@Path("name") name: String): PokemonDetailResponse
}