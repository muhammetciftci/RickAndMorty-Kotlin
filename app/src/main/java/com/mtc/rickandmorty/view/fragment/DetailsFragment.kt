package com.mtc.rickandmorty.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.mtc.rickandmorty.R
import com.mtc.rickandmorty.databinding.FragmentDetailsBinding
import com.mtc.rickandmorty.model.character.CharacterModel
import com.mtc.rickandmorty.utils.Util.Companion.convertDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    val bundle: DetailsFragmentArgs by navArgs()
    private lateinit var character: CharacterModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.detailsFragment = this

        character = getCharBundle()
        setCharBundle()

        return binding.root
    }

    fun backButtonClick() {
        findNavController().navigateUp()
    }

    fun getCharBundle(): CharacterModel {
        return bundle.character
    }

    fun setCharBundle() {
        val episodes = character.episode.map { url ->
            val parts = url.split("/")
            parts.last().toString()
        }
        val genderColor = when (character.gender) {
            "Male" -> R.color.male_color
            "Female" -> R.color.female_color
            "Genderless" -> R.color.genderless_color
            "unknown" -> R.color.unkown_color
            else -> R.color.theme_blue_light2
        }

        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), genderColor)
        Glide.with(requireContext()).load(character.image).into(binding.charImageView)

        binding.apply {
            characterModel = character
            episodesTextView.text = episodes.toString()
            createdAtTextView.text = convertDate(character.created)
            binding.toolbar2.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), genderColor)
        }
    }


}