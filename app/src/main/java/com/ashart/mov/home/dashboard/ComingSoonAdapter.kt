package com.ashart.mov.home.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ashart.mov.R
import com.ashart.mov.home.model.Film
import com.bumptech.glide.Glide

class ComingSoonAdapter(private val data : List<Film>)
    : RecyclerView.Adapter<NowPlayingAdapter.LeagueViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): LeagueViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.row_item_coming_soon, viewGroup, false)
        return LeagueViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        val (desc, genre, judul, poster, rating) = data[position]

        Glide.with(holder.itemView.context)
            .load(poster)
            .into(holder.tvImage)

        holder.tvTitle.text = judul
        holder.tvGenre.text = genre
        holder.tvRate.text = rating
        holder.tvDesc.text= desc
    }

    inner class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tv_kursi)
        val tvGenre: TextView = view.findViewById(R.id.tv_genre)
        val tvRate: TextView = view.findViewById(R.id.tv_rate)
        val tvImage: ImageView = view.findViewById(R.id.iv_poster_image)
        val tvDesc: TextView = view.findViewById(R.id.tv_desc)
    }
}