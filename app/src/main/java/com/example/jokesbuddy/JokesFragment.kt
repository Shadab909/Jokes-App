package com.example.jokesbuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.jokesbuddy.adapter.JokesRecyclerViewAdapter
import com.example.jokesbuddy.databinding.FragmentJokesBinding
import com.example.jokesbuddy.model.Joke

class JokesFragment : Fragment() {

    private lateinit var binding: FragmentJokesBinding
    private val args: JokesFragmentArgs by navArgs()
    private lateinit var url : String
    private lateinit var mAdapter : JokesRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_jokes, container, false)
        (activity as AppCompatActivity).supportActionBar?.hide()

        mAdapter = JokesRecyclerViewAdapter()
        url = args.url
        val category = args.category

        binding.category.text = category
        binding.backBtn.setOnClickListener { findNavController().popBackStack() }

        initRecyclerView()

        binding.swipe.setOnRefreshListener {
            jokeList()
            binding.swipe.isRefreshing = false
        }

        return binding.root
    }

    private fun initRecyclerView(){
        binding.jokesRv.apply {
            layoutManager = LinearLayoutManager(context)
            jokeList()
            adapter = mAdapter
        }
    }

    private fun jokeList(){
        binding.progressBar.visibility = View.VISIBLE
        val request = object : JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Success Part
                val jokesJsonArray = response.getJSONArray("jokes")
                val jokesArray = ArrayList<Joke>()
                for(i in 0 until jokesJsonArray.length()) {
                    val jokesJsonObject = jokesJsonArray.getJSONObject(i)
                    val joke = Joke(
                        jokesJsonObject.getString("joke")
                    )
                    jokesArray.add(joke)
                }

                mAdapter.submitList(jokesArray)
                binding.progressBar.visibility = View.GONE
            },

            Response.ErrorListener {
                // Failure Part
                Toast.makeText(
                    context,
                    "error while parsing the jsonObject/array",
                    Toast.LENGTH_LONG
                ).show()
            }
        ) {
            // Providing Request Headers
            override fun getHeaders(): Map<String, String> {
                // Create HashMap of your Headers as the example provided below
                val headers = HashMap<String, String>()
                headers["User-Agent"]="Mozilla/5.0"
                return headers
            }
        }
        context?.let {
            MySingleton.getInstance(it).addToRequestQueue(request)
        }
    }
}