package solis.jhon.pokezeus.domain.model

import solis.jhon.pokezeus.data.network.model.PokemonResponse

data class PokemonResultModel (
    val next: String?,
    val results: List<PokemonResponse>?
)