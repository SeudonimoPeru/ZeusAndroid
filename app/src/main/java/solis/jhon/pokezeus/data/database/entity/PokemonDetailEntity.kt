package solis.jhon.pokezeus.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_detail")
data class PokemonDetailEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val pokemonId: Int? = 0,
    val name: String? = null,
    val image: String? = null,
    val imageDefault: String? = null,
    val height: Int? = 0,
    val weight: Int? = 0,
    val experience: Int? = 0,
    val types: String? = null,
)