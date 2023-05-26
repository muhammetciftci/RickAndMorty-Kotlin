package com.mtc.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mtc.rickandmorty.R
import com.mtc.rickandmorty.model.character.CharacterModel

class CharacterAdapter(
    var characters: List<CharacterModel>,
    val listener: IChacterAdapterListener
) :
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    private val MALE_GENDER = 0
    private val FEMALE_GENDER = 1
    private val GENDERLESS_GENDER = 2
    private val UNKOWN_GENDER = 3

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val charName: TextView = view.findViewById(R.id.charNameTextView)
        val charImage: ImageView = view.findViewById(R.id.charImageView)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            MALE_GENDER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_character_male, parent, false)
                ViewHolder(view)
            }

            FEMALE_GENDER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_character_female, parent, false)
                ViewHolder(view)
            }

            GENDERLESS_GENDER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_character_genderless, parent, false)
                ViewHolder(view)
            }

            UNKOWN_GENDER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_character_unkown, parent, false)
                ViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characters[position]
        holder.charName.text = character.name
        Glide.with(holder.itemView.context).load(character.image)
            .into(holder.charImage)

        holder.itemView.setOnClickListener {
            listener.onClickChar(character)
        }
    }

    override fun getItemCount(): Int = characters.size


    override fun getItemViewType(position: Int): Int {
        val character = characters[position]
        return when (character.gender) {
            "Male" -> MALE_GENDER
            "Female" -> FEMALE_GENDER
            "Genderless" -> GENDERLESS_GENDER
            "unknown" -> UNKOWN_GENDER
            else -> throw IllegalArgumentException("Invalid gender type")
        }
    }



}