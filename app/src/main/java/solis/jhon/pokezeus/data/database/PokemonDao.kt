package solis.jhon.pokezeus.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import solis.jhon.pokezeus.data.database.entity.PokemonDetailEntity
import solis.jhon.pokezeus.data.database.entity.PokemonEntity

@Dao
interface PokemonDao {

    @Upsert
    suspend fun savePokemons(pokemons: List<PokemonEntity>)

    @Upsert
    suspend fun savePokemonDetail(pokemon: PokemonDetailEntity)

    @Query("SELECT * FROM pokemon ORDER BY id ASC LIMIT :limit OFFSET :offset")
    fun getPokemon(limit: Int, offset: Int) : Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemonDetail WHERE `name` LIKE :name")
    fun getPokemonDetail(name: String) : Flow<PokemonDetailEntity>

}