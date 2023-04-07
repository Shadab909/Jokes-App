package com.example.jokesbuddy.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jokesbuddy.databinding.JokesItemLayoutBinding
import com.example.jokesbuddy.model.Joke

class JokesRecyclerViewAdapter : ListAdapter<Joke, JokesRecyclerViewAdapter.JokesViewHolder>(JokesDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokesViewHolder {
        val layoutInflater =  LayoutInflater.from(parent.context)
        val binding = JokesItemLayoutBinding.inflate(layoutInflater,parent,false)
        return JokesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JokesViewHolder, position: Int) {
        val joke = getItem(position)
        holder.jokeText.text = joke.jokeText
        holder.shareBtn.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, joke.jokeText)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            holder.shareBtn.context.startActivity(shareIntent)
        }
    }

    class JokesViewHolder(private val binding : JokesItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val jokeText = binding.jokeText
        val shareBtn = binding.shareBtn
    }
}

class JokesDiffUtilCallBack : DiffUtil.ItemCallback<Joke>() {
    override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
        return oldItem.jokeText == newItem.jokeText
    }
}


