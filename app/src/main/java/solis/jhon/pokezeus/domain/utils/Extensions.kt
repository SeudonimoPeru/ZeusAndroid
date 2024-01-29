package solis.jhon.pokezeus.domain.utils

import android.graphics.Color
import androidx.core.graphics.ColorUtils
import solis.jhon.pokezeus.data.network.PokemonService
import solis.jhon.pokezeus.data.network.model.TypeItemResponse
import solis.jhon.pokezeus.data.network.model.TypeResponse

fun String.defaultIfEmpty(defaultValue: String): String {
    return if (this.isNotEmpty()) {
        this
    } else {
        defaultValue
    }
}

fun String.getOffset(): String {
    val arrayString = this.replace(PokemonService.BASE_URL, "").split("&")
    val regex = Regex("\\d+")
    var matchResult = "0"
    if (arrayString.isNotEmpty()) {
        matchResult = regex.find(arrayString.first())?.value ?: "0"
    }
    return matchResult
}

fun String.replaceBaseUrlImage(): String {
    val image = this.replace(PokemonService.BASE_URL + "pokemon/", "").replace("/", ".png")
    return "${PokemonService.IMAGE_BASE_URL}$image"
}

fun String.twoFirstLetter(): String {
    return if (this.length >= 2) {
        return this.substring(0, 2)
    } else {return this
    }
}

fun String.toArrayTypeResponse(): List<TypeResponse> {
    val list = mutableListOf<TypeResponse>()
    val arrayString = this.split(",")

    if (arrayString.isNotEmpty()) {
        arrayString.forEach {
            list.add(TypeResponse(TypeItemResponse(it)))
        }
    }

    return list
}

fun List<TypeResponse>.asString(): String {
    val types = StringBuilder()
    types.append("")

    this.forEach {
        if (types.isEmpty()) {
            types.append("${it.type?.name}")
        } else {
            types.append(", ${it.type?.name}")
        }
    }
    return types.toString()
}

fun Int.applyOpacity(opacity: Float): Int {
    val colorBlanco = Color.WHITE
    return ColorUtils.blendARGB(colorBlanco, this, opacity)
}