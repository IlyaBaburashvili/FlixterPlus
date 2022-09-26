package com.example.flixter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BestMovieRecyclerViewAdapter (
    private val movies: List<BestMovie>,
    private val mListener: OnListFragmentInteractionListener?
    )
    : RecyclerView.Adapter<BestMovieRecyclerViewAdapter.MovieViewHolder>()
    {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.top_movie, parent, false)
            return MovieViewHolder(view)
        }

        inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
            var mItem: BestMovie? = null
            val mMovieTitle: TextView = mView.findViewById<View>(R.id.MovieTitle) as TextView
            val mMovieDescription: TextView = mView.findViewById<View>(R.id.MovieDescription) as TextView
            val mMoviePoster: ImageView = mView.findViewById<View>(R.id.MovieImage) as ImageView

        }

        override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
            val movie = movies[position]

            holder.mItem = movie
            holder.mMovieTitle.text = movie.title
            holder.mMovieDescription.text = movie.description

            Glide.with(holder.mView)
                .load(movie.full_link)
                .centerInside()
                .into(holder.mMoviePoster)

            holder.mView.setOnClickListener {
                holder.mItem?.let { book ->
                    mListener?.onItemClick(book)
                }
            }
        }

        override fun getItemCount(): Int {
            return movies.size
        }
}