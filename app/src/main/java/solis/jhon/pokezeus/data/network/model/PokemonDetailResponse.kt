package solis.jhon.pokezeus.data.network.model

import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("weight")
    val weight: Int?,
    @SerializedName("base_experience")
    val experience: Int?,
    @SerializedName("sprites")
    val sprites: SpritesResponse?,
    @SerializedName("types")
    val types: List<TypeResponse>?
)

data class SpritesResponse(
    @SerializedName("other")
    val other: OtherResponse?,
    @SerializedName("front_default")
    val frontDefault: String?
)

data class TypeResponse(
    @SerializedName("type")
    val type: TypeItemResponse?
)

data class TypeItemResponse(
    @SerializedName("name")
    val name: String?
)

data class OtherResponse(
    @SerializedName("dream_world")
    val dream: DreamWorldResponse?
)

data class DreamWorldResponse(
    @SerializedName("front_default")
    val front: String?
)