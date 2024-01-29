package solis.jhon.pokezeus.domain.mapper

import solis.jhon.pokezeus.data.database.entity.PokemonEntity
import solis.jhon.pokezeus.data.network.model.PokemonListResponse
import solis.jhon.pokezeus.data.network.model.PokemonResponse
import solis.jhon.pokezeus.domain.model.PokemonListModel
import solis.jhon.pokezeus.domain.model.PokemonModel
import solis.jhon.pokezeus.domain.utils.replaceBaseUrlImage

fun PokemonListResponse.toDomain() = PokemonListModel(
    next = next,
    results = results?.map { it.toDomain() }
)

fun PokemonResponse.toDomain() = PokemonModel(
    name = name,
    url = url,
    image = url?.replaceBaseUrlImage(),
    favorite = false
)

fun PokemonResponse.asEntity() = PokemonEntity(
    name = name,
    url = url
)

fun PokemonEntity.toResponse() = PokemonResponse(
    name = name,
    url = url
)
