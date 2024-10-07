package com.behruzbek0430.movieapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.behruzbek0430.movieapp.R
import com.behruzbek0430.movieapp.models.Movie
import com.squareup.picasso.Picasso

class ImageAdapter(val rvGo: RvGo, private val movies: List<Movie>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val movie = movies[position]
        Picasso.get()
            .load(movie.image1)
            .into(holder.imageView)
        holder.imageView.setOnClickListener {
            rvGo.onClick(movie.id!!)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)

    }

    interface RvGo{
        fun onClick(id: String)
    }
}