package solis.jhon.pokezeus.presentation.feature.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import solis.jhon.pokezeus.R
import solis.jhon.pokezeus.databinding.ItemPokemonBinding
import solis.jhon.pokezeus.domain.model.PokemonModel
import solis.jhon.pokezeus.domain.utils.applyOpacity
import solis.jhon.pokezeus.domain.utils.twoFirstLetter

class PokemonAdapter(
    val callback: (pokemon: PokemonModel) -> Unit,
    val context: Context,
    val backgroundColorSelected: Int,
    val initialsColorSelected: Int,
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    private val items = mutableListOf<PokemonModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun bindItems(nItems: List<PokemonModel>) {
        items.clear()
        items.addAll(nItems)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun bindNewItem(item: PokemonModel) {
        items.add(item)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun bindNewItems(nItems: List<PokemonModel>) {
        nItems.forEach {
            items.add(it)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(parent)
    }

    inner class PokemonViewHolder(
        val parent: ViewGroup,
        val binding: ItemPokemonBinding= ItemPokemonBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: PokemonModel) {
            binding.apply {
                pokemon = data
                var backgroundColor = context.getColor(R.color.md_theme_primaryContainer)

                if(backgroundColorSelected != 0) {
                    backgroundColor = backgroundColorSelected
                }
                backgroundColor.let { cvPokemon.setCardBackgroundColor(it) }
                cvPokemon.setOnClickListener {
                    callback(data)
                }
                if (data.image.isNullOrEmpty()) {
                    createImageWithInitials(data.name)
                } else {
                    tvInitialsPokemon.visibility = View.GONE
                    Glide.with(context)
                        .load(data.image)
                        .placeholder(R.drawable.placeholder_default)
                        .listener(object : RequestListener<Drawable?> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable?>,
                                isFirstResource: Boolean
                            ): Boolean {
                                tvInitialsPokemon.visibility = View.VISIBLE
                                createImageWithInitials(data.name)
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable,
                                model: Any,
                                target: Target<Drawable?>?,
                                dataSource: DataSource,
                                isFirstResource: Boolean
                            ): Boolean {
                                return false
                            }
                        })
                        .into(ivPokemon)
                }
            }
        }

        fun createImageWithInitials(name: String?) {
            binding.apply {
                if (name.isNullOrEmpty()) {
                    ivPokemon.setImageDrawable(context.getDrawable(R.drawable.placeholder_default))
                } else {
                    var colorInitials = context.getColor(R.color.md_theme_primary)

                    if (initialsColorSelected != 0) {
                        colorInitials = initialsColorSelected
                    }

                    ivPokemon.visibility = View.GONE
                    tvInitialsPokemon.text = name.twoFirstLetter()
                    tvInitialsPokemon.setTextColor(colorInitials)
                    cvPokemonImage.strokeColor = colorInitials
                    cvPokemonImage.strokeWidth = 12
                    cvPokemonImage.setCardBackgroundColor(colorInitials.applyOpacity(0.4F))
                }
            }
        }
    }
}