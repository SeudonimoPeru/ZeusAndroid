package solis.jhon.pokezeus.data.database.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import solis.jhon.pokezeus.data.database.entity.PokemonDetailEntity

@Dao
interface PokemonDetailDao {

    @Insert
    suspend fun insertPokemonDetail(pokemon: PokemonDetailEntity)

    @Update
    suspend fun updatePokemonDetail(pokemon: PokemonDetailEntity)

    @Query("SELECT * FROM pokemon_detail WHERE `name` LIKE :name")
    fun findPokemonDetail(name: String) : List<PokemonDetailEntity>?

}