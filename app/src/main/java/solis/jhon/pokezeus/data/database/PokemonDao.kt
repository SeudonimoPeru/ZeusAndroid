package solis.jhon.pokezeus.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import solis.jhon.pokezeus.data.database.entity.PokemonDetailEntity
import solis.jhon.pokezeus.data.database.entity.PokemonEntity

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePokemons(pokemons: List<PokemonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePokemonDetail(pokemon: PokemonDetailEntity)

    @Query("SELECT * FROM pokemon LIMIT :limit OFFSET :offset")
    fun getPokemon(limit: Int, offset: Int) : List<PokemonEntity>?

    /*@Query("SELECT * FROM pokemonDetail WHERE `name` LIKE :name")
    fun getPokemonDetail(name: String) : PokemonDetailEntity?*/

}