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

class ComingSoonAdapter(private val listFilm: List<Film>) : RecyclerView.Adapter<ComingSoonAdapter.LeagueViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_item_coming_soon, parent, false)
        return LeagueViewHolder(view)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        val film = listFilm[position]

        Glide.with(holder.itemView.context)
            .load(film.poster)
            .into(holder.tvImage)

        holder.tvTitle.text = film.judul
        holder.tvGenre.text = film.genre
        holder.tvRate.text = film.rating
        holder.tvDesc.text= film.desc
    }

    override fun getItemCount(): Int {
        return listFilm.size
    }

    inner class LeagueViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.tv_kursi)
        var tvGenre: TextView = itemView.findViewById(R.id.tv_genre)
        var tvRate: TextView = itemView.findViewById(R.id.tv_rate)
        var tvImage: ImageView = itemView.findViewById(R.id.iv_poster_image)
        var tvDesc: TextView = itemView.findViewById(R.id.tv_desc)
    }
}