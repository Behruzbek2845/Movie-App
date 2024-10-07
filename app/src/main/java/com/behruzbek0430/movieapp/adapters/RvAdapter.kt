package com.behruzbek0430.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.behruzbek0430.movieapp.databinding.ItemRvBinding
import com.behruzbek0430.movieapp.models.Movie
import com.squareup.picasso.Picasso

class RvAdapter(var rvAction: RvAction, val list: ArrayList<Movie>): RecyclerView.Adapter<RvAdapter.VH>() {
    inner class VH(var itemRvBinding: ItemRvBinding): RecyclerView.ViewHolder(itemRvBinding.root){
        fun OnBind(user: Movie){
            Picasso.get().load(user.image1).into(itemRvBinding.image)
            itemRvBinding.root.setOnClickListener {
                rvAction.onClick(user.id!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.OnBind(list[position])
    }

    interface RvAction{
        fun onClick(id: String)

    }

}