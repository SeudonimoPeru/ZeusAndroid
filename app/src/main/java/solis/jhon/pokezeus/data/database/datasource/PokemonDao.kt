package solis.jhon.pokezeus.data.database.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import solis.jhon.pokezeus.data.database.entity.PokemonEntity

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePokemons(pokemons: List<PokemonEntity>)

    @Query("SELECT * FROM pokemon LIMIT :limit OFFSET :offset")
    fun getPokemon(limit: Int, offset: Int) : List<PokemonEntity>?


}