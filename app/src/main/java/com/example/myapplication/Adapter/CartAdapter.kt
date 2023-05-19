package com.example.myapplication.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Database.GetDish
import com.example.myapplication.Database.UpdateCart
import com.example.myapplication.Domain.CartDomain
import com.example.myapplication.R
import kotlin.collections.ArrayList

class CartAdapter(var cartDomain: ArrayList<CartDomain>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflate =
                LayoutInflater.from(parent.context).inflate(R.layout.viewholder_card, parent, false)
            return ViewHolder(inflate)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            var getDish = GetDish(cartDomain[position].IdDish.toString().toInt())

            getDish.getDishByIdForCart { dish ->
                holder.title.text = dish.Name
                holder.feeEachItem.text = dish.Price.toString()
                holder.totalEachItem.text = (Math.round(dish.Price * cartDomain[position].Number * 100.0) / 100.0).toString()
                holder.num.text = cartDomain[position].Number.toString()
                val drawableResourceId = holder.itemView.context.resources.getIdentifier(
                    dish.Image,"drawable", holder.itemView.context.packageName
                )
                Glide.with(holder.itemView.context).load(drawableResourceId).into(holder.pic)
            }


            holder.plusItem.setOnClickListener() {
                UpdateCart( cartDomain[position].Id, cartDomain[position].IdDish, cartDomain[position].IdUser,  cartDomain[position].Number,"add" )
            }
            holder.minusItem.setOnClickListener() {
                UpdateCart( cartDomain[position].Id, cartDomain[position].IdDish, cartDomain[position].IdUser,  cartDomain[position].Number,"minus" )
            }
        }



    override fun getItemCount(): Int {
            return cartDomain.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var title: TextView
            var feeEachItem: TextView
            var pic: ImageView
            var plusItem: ImageView
            var minusItem: ImageView
            var totalEachItem: TextView
            var num: TextView

            init {
                title = itemView.findViewById(R.id.title2Txt)
                feeEachItem = itemView.findViewById(R.id.feeEachItem)
                pic = itemView.findViewById(R.id.picCard)
                totalEachItem = itemView.findViewById(R.id.totalEachItem)
                num = itemView.findViewById(R.id.numberItemTxt)
                plusItem = itemView.findViewById(R.id.plusCardBtn)
                minusItem = itemView.findViewById(R.id.minusCardBtn)
            }
        }
}