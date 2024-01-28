package solis.jhon.pokezeus.domain.mapper

import solis.jhon.pokezeus.data.database.entity.PokemonDetailEntity
import solis.jhon.pokezeus.data.database.entity.PokemonEntity
import solis.jhon.pokezeus.data.network.model.PokemonDetailResponse
import solis.jhon.pokezeus.data.network.model.PokemonResponse
import solis.jhon.pokezeus.domain.model.PokemonDetailModel
import solis.jhon.pokezeus.domain.model.PokemonModel

fun PokemonResponse.asEntity() = PokemonEntity(
    name = name,
    url = url
)

fun PokemonEntity.toDomain() = PokemonModel(
    id = id,
    name = name,
    url = url,
    favorite = favorite
)

fun PokemonDetailResponse.asEntity() = PokemonDetailEntity(
    id = id,
    name = name,
    image = sprites?.other?.dream?.front,
    height = height,
    weight = weight,
    experience = experience,
)

fun PokemonDetailEntity.toDomain() = PokemonDetailModel(
    id = id,
    name = name,
    height = height,
    weight = weight,
    experience = experience,
    image = image
)