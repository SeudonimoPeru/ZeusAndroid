package solis.jhon.pokezeus.data.database.entity

import androidx.room.Entity

@Entity(tableName = "pokemonDetail")
data class PokemonDetailEntity (
    val id: Int? = 0,
    val name: String? = null,
    val image: String? = null,
    val height: Int? = 0,
    val weight: Int? = 0,
    val experience: Int? = 0,
)