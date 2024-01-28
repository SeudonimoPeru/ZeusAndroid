package solis.jhon.pokezeus.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import solis.jhon.pokezeus.data.database.entity.PokemonDetailEntity
import solis.jhon.pokezeus.data.database.entity.PokemonEntity

@Database(entities = [PokemonEntity::class, PokemonDetailEntity::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase(){

    companion object {
        const val DATABASE_NAME = "pokemon_db"
    }

    abstract fun pokemonDao() : PokemonDao
}