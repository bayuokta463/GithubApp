package com.bayuokta.githubapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bayuokta.githubapp.R
import com.bayuokta.githubapp.databinding.ItemUserBinding
import com.bayuokta.githubapp.model.ItemsItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class UserAdapter: RecyclerView.Adapter<UserAdapter.ViewHolder>(){
    private val listUser = ArrayList<ItemsItem?>()

    fun setData(item: List<ItemsItem?>){
        listUser.clear()
        listUser.addAll(item)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserBinding.bind(itemView)
        fun bind(item: ItemsItem?) {
            with(binding){
                Glide.with(itemView)
                    .load(item?.avatarUrl)
                    .apply(RequestOptions().override(62,62))
                    .into(profileImage)

                tvUsername.text = item?.login

            }
        }

    }
}