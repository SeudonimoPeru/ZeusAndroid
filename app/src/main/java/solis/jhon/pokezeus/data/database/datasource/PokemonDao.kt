package solis.jhon.pokezeus.data.database.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import solis.jhon.pokezeus.data.database.entity.PokemonEntity

@Dao
interface PokemonDao {

    @Insert
    suspend fun insertPokemon(pokemon: PokemonEntity)

    @Update
    suspend fun updatePokemon(pokemon: PokemonEntity)

    @Query("UPDATE pokemon SET favorite = :favorite WHERE `name` = :name")
    suspend fun updatePokemon(favorite: Boolean, name: String)

    @Query("SELECT * FROM pokemon WHERE `name` = :name")
    suspend fun findPokemon(name: String) : List<PokemonEntity>?

    @Query("SELECT * FROM pokemon LIMIT :limit OFFSET :offset")
    fun getPokemon(limit: Int, offset: Int) : List<PokemonEntity>?


}