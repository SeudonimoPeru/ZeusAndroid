package solis.jhon.pokezeus.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import solis.jhon.pokezeus.data.database.datasource.PokemonDao
import solis.jhon.pokezeus.data.database.datasource.PokemonDetailDao
import solis.jhon.pokezeus.data.network.PokemonService
import solis.jhon.pokezeus.data.network.model.DreamWorldResponse
import solis.jhon.pokezeus.data.network.model.OtherResponse
import solis.jhon.pokezeus.data.network.model.PokemonDetailResponse
import solis.jhon.pokezeus.data.network.model.PokemonListResponse
import solis.jhon.pokezeus.data.network.model.PokemonResponse
import solis.jhon.pokezeus.data.network.model.SpritesResponse
import solis.jhon.pokezeus.domain.mapper.asEntity
import solis.jhon.pokezeus.domain.mapper.toResponse
import solis.jhon.pokezeus.domain.model.PokemonModel
import solis.jhon.pokezeus.domain.repository.PokemonRepository
import solis.jhon.pokezeus.domain.utils.toArrayTypeResponse
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonService: PokemonService,
    private val pokemonDao: PokemonDao,
    private val pokemonDetailDao: PokemonDetailDao
): PokemonRepository {

    override suspend fun pokemonList(): Flow<PokemonListResponse> = flow {
        emit(pokemonService.pokemonList())
    }.flowOn(Dispatchers.IO)

    override suspend fun pokemonList(offset: String): Flow<PokemonListResponse> = flow {
        emit(pokemonService.pokemonList(offset = offset.toInt()))
    }.flowOn(Dispatchers.IO)

    override suspend fun savePokemonList(data: PokemonListResponse) : Flow<PokemonListResponse> = flow {
        val results = arrayListOf<PokemonResponse>()
        data.results?.let { list ->
            list.forEach { pokemon ->
                pokemon.name?.let {
                    val findList = pokemonDao.findPokemon(it)
                    if (findList.isNullOrEmpty()) {
                        pokemonDao.insertPokemon(pokemon.asEntity())
                        results.add(pokemon)
                    } else {
                        results.add(findList.first().toResponse())
                    }
                }
            }
        }
        data.apply {
            emit(PokemonListResponse(count = count, next = next, previous = previous, results = results))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun savePokemon(data: PokemonModel) {
        data.name?.let { pokemonDao.updatePokemon(data.favorite, it) }
    }

    override suspend fun getPokemonList(limit: Int, offset: Int): Flow<PokemonListResponse> = flow {
        emit(PokemonListResponse(count = null, next = (offset + 1).toString(), previous = null , results = pokemonDao.getPokemon(limit, offset)
            ?.map { it.toResponse() }))
    }.flowOn(Dispatchers.IO)

    override suspend fun pokemonDetail(name: String): Flow<PokemonDetailResponse> = flow {
        emit(pokemonService.pokemonDetail(name))
    }.flowOn(Dispatchers.IO)

    override suspend fun savePokemonDetail(data: PokemonDetailResponse) : Flow<PokemonDetailResponse> = flow {
        data.name?.let {
            val findList = pokemonDetailDao.findPokemonDetail(it)
            if (findList.isNullOrEmpty()) {
                pokemonDetailDao.insertPokemonDetail(data.asEntity())
                emit(data)
            } else {
                findList.first().apply {
                    emit(PokemonDetailResponse(id = pokemonId, name = name, height = height, weight = weight, experience = experience, sprites = SpritesResponse(other =
                    OtherResponse(DreamWorldResponse(front = image)), frontDefault = imageDefault), types = types?.toArrayTypeResponse()))
                }
            }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getPokemonDetail(name: String): Flow<PokemonDetailResponse> = flow {
        val list = pokemonDetailDao.findPokemonDetail(name)
        if (!list.isNullOrEmpty()) {
            val pokemonDetail = list.first()
            pokemonDetail.apply {
                emit(PokemonDetailResponse(id = pokemonId, name = name, height = height, weight = weight, experience = experience, sprites = SpritesResponse(other =
                    OtherResponse(DreamWorldResponse(front = image)), frontDefault = imageDefault), types = types?.toArrayTypeResponse()
                ))
            }
        }
    }.flowOn(Dispatchers.IO)

}