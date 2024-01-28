package solis.jhon.pokezeus.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name : String? = null,
    val url: String? = null,
    val favorite: Boolean = false
)