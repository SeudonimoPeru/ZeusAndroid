package solis.jhon.pokezeus.domain.mapper

import solis.jhon.pokezeus.data.database.entity.PokemonDetailEntity
import solis.jhon.pokezeus.data.database.entity.PokemonEntity
import solis.jhon.pokezeus.data.network.model.PokemonDetailResponse
import solis.jhon.pokezeus.data.network.model.PokemonListResponse
import solis.jhon.pokezeus.data.network.model.PokemonResponse
import solis.jhon.pokezeus.domain.model.PokemonDetailModel
import solis.jhon.pokezeus.domain.model.PokemonListModel
import solis.jhon.pokezeus.domain.model.PokemonModel
import solis.jhon.pokezeus.domain.utils.asString
import solis.jhon.pokezeus.domain.utils.replaceBaseUrlImage

fun PokemonListResponse.toDomain() = PokemonListModel(
    next = next,
    results = results?.map { it.toDomain() }
)

fun PokemonResponse.toDomain() = PokemonModel(
    name = name,
    url = url,
    image = url?.replaceBaseUrlImage(),
    favorite = favorite
)

fun PokemonResponse.asEntity() = PokemonEntity(
    name = name,
    url = url
)

fun PokemonEntity.toResponse() = PokemonResponse(
    name = name,
    url = url,
    favorite = favorite
)

fun PokemonModel.asEntity() = PokemonEntity(
    name = name,
    url = url,
    favorite = favorite
)

fun PokemonDetailResponse.asEntity() = PokemonDetailEntity(
    pokemonId = id,
    name = name,
    image = sprites?.other?.dream?.front,
    imageDefault = sprites?.frontDefault,
    height = height,
    weight = weight,
    experience = experience,
    types = types?.asString()
)

fun PokemonDetailResponse.toDomain() = PokemonDetailModel(
    id = id,
    name = name,
    height = height,
    weight = weight,
    experience = experience,
    image = sprites?.other?.dream?.front,
    imageDefault = sprites?.frontDefault,
    types = types?.asString()
)