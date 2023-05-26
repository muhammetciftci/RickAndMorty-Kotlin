package com.mtc.rickandmorty.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.mtc.rickandmorty.R
import com.mtc.rickandmorty.adapter.CharacterAdapter
import com.mtc.rickandmorty.adapter.LocationCategoryAdapter
import com.mtc.rickandmorty.databinding.FragmentMainBinding
import com.mtc.rickandmorty.model.character.CharacterModel
import com.mtc.rickandmorty.model.location.LocationModel
import com.mtc.rickandmorty.adapter.IChacterAdapterListener
import com.mtc.rickandmorty.adapter.ILocationAdapterListener
import com.mtc.rickandmorty.viewmodel.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), IChacterAdapterListener, ILocationAdapterListener {

    private lateinit var locationCategoryAdapter: LocationCategoryAdapter
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var characters: List<CharacterModel>
    private var locationIsLoading = false

    private val viewModel by viewModels<MainFragmentViewModel>()

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.mainFragment = this

        setDefaultStatusBar() // değişkenlik gösterdiği için - because it varies
        setupLocationCategoryRecyclerView()
        setupCharacterRecyclerView()
        observe()
        searchListener()

        return binding.root
    }

    fun observe() {
        viewModel.locations.observe(viewLifecycleOwner, Observer {
            locationCategoryAdapter.locations = it
            binding.locationCategoryAdapter = locationCategoryAdapter
        })

        viewModel.character.observe(viewLifecycleOwner, Observer {
            characters = it // for filter - filtre için
            characterAdapter.characters = it
            binding.characterAdapter = characterAdapter
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireActivity().applicationContext, it, Toast.LENGTH_SHORT).show()
            binding.tryAgainButton.visibility = View.VISIBLE
        })

        viewModel.locationIsLoading.observe(viewLifecycleOwner, Observer {
            locationIsLoading = it
        })

        viewModel.characterIsLoading.observe(viewLifecycleOwner, Observer { isTrue ->
            if (isTrue) {
                binding.charProgressBar.visibility = View.VISIBLE
            } else {
                binding.charProgressBar.visibility = View.GONE
            }
        })
    }

    fun searchListener() {
        binding.searchEditText.addTextChangedListener { text ->
            val filteredList = if (text.isNullOrEmpty()) {
                characters // Arama kelimesi yoksa, tüm liste öğelerini döndür - If there is no search word, return all list items
            } else {
                characters.filter { it.name.contains(text, ignoreCase = true) }
            }
            characterAdapter.characters = filteredList
            characterAdapter.notifyDataSetChanged()
        }
    }

    fun setupLocationCategoryRecyclerView() {
        locationCategoryAdapter = LocationCategoryAdapter(emptyList(), this)
        binding.locationCategoryRecylerView.adapter = locationCategoryAdapter
    }

    fun setupCharacterRecyclerView() {
        characterAdapter = CharacterAdapter(emptyList(), this)
        binding.characterAdapter = characterAdapter
    }

    override fun onClickChar(char: CharacterModel) {
        val directions =
            MainFragmentDirections.actionMainFragmentToDetailsFragment(character = char)
        Navigation.findNavController(binding.root).navigate(directions)
    }

    override fun onClickLocation(location: LocationModel) {
        val locationName = location.name
        val newCharList = characters.filter { it.location.name == locationName }
        characterAdapter.characters = newCharList
        characterAdapter.notifyDataSetChanged()
    }

    override fun sendLocationRequest() {
        viewModel.getLocations()
    }

    override fun loadingListener(): Boolean {
        return locationIsLoading
    }

    private fun setDefaultStatusBar() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.theme_blue_light2)
    }

    fun tryAgainButtonClick() {
        viewModel.tryAgain()
        binding.tryAgainButton.visibility = View.GONE
    }


}