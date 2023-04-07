package com.example.jokesbuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.jokesbuddy.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        (activity as AppCompatActivity).supportActionBar?.hide()
        binding.cardProg.setOnClickListener {
            val url = "https://v2.jokeapi.dev/joke/Programming?blacklistFlags=explicit&type=single&amount=10"
            val category = "Programming Jokes"
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToJokesFragment(url,category))
        }


        binding.cardDark.setOnClickListener {
            val url = "https://v2.jokeapi.dev/joke/Dark?blacklistFlags=explicit&type=single&amount=10"
            val category = "Dark Jokes"
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToJokesFragment(url,category))
        }


        binding.cardMisc.setOnClickListener {
            val url = "https://v2.jokeapi.dev/joke/Miscellaneous?blacklistFlags=explicit&type=single&amount=10"
            val category = "Miscellaneous Jokes"
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToJokesFragment(url,category))
        }

        binding.cardPun.setOnClickListener {
            val url = "https://v2.jokeapi.dev/joke/Pun?blacklistFlags=explicit&type=single&amount=10"
            val category = "Pun Jokes"
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToJokesFragment(url,category))
        }

        return binding.root
    }



}