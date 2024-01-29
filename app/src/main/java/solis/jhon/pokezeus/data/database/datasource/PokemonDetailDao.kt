package solis.jhon.pokezeus.data.database.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import solis.jhon.pokezeus.data.database.entity.PokemonDetailEntity

@Dao
interface PokemonDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePokemonDetail(pokemon: PokemonDetailEntity)

    @Query("SELECT * FROM pokemon_detail WHERE `name` LIKE :name")
    fun getPokemonDetail(name: String) : List<PokemonDetailEntity>?

}