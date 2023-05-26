package com.mtc.rickandmorty.view.fragment

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mtc.rickandmorty.R
import com.mtc.rickandmorty.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var binding:FragmentSplashBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater,container,false)

        Handler().postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
        },3000)

        return binding.root
    }




}