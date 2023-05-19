package com.example.projectfood.Adapter


import android.widget.TextView

import com.example.carefood.R
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.example.projectfood.Domain.CategoryDomain
import androidx.core.content.ContextCompat
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.carefood.Domain.Category
import java.util.ArrayList

class CategoryAdapter(var categorys: ArrayList<Category>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_cat, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.categoryName.text = categorys[position].Name
        var picUrl = ""
        when (position) {
            0 -> {
                picUrl = "cat_1"
                holder.mainLayout.background =
                    ContextCompat.getDrawable(
                        holder.itemView.context,
                        R.drawable.category_background1
                    )
            }
            1 -> {
                picUrl = "cat_2"
                holder.mainLayout.background =
                    ContextCompat.getDrawable(
                        holder.itemView.context,
                        R.drawable.category_background2
                    )
            }
            2 -> {
                picUrl = "cat_3"
                holder.mainLayout.background =
                    ContextCompat.getDrawable(
                        holder.itemView.context,
                        R.drawable.category_background3
                    )
            }
            3 -> {
                picUrl = "cat_4"
                holder.mainLayout.background =
                    ContextCompat.getDrawable(
                        holder.itemView.context,
                        R.drawable.category_background4
                    )
            }
            4 -> {
                picUrl = "cat_5"
                holder.mainLayout.background =
                    ContextCompat.getDrawable(
                        holder.itemView.context,
                        R.drawable.category_background5
                    )
            }
        }
        val drawableRecouseId = holder.itemView.context.resources.getIdentifier(
            picUrl, "drawable",
            holder.itemView.context.packageName
        )
        Glide.with(holder.itemView.context).load(drawableRecouseId).into(holder.categoryPic)
    }

    override fun getItemCount(): Int {
        return categorys.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoryName: TextView
        var categoryPic: ImageView
        var mainLayout: ConstraintLayout

        init {
            categoryName = itemView.findViewById(R.id.categoryName)
            categoryPic = itemView.findViewById(R.id.categoryPic)
            mainLayout = itemView.findViewById(R.id.mainLayout)
        }
    }
}