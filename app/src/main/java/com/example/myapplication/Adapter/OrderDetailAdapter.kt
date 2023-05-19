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
import com.example.myapplication.Domain.OrderDetailDomain
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_detail_dish.*

class OrderDetailAdapter(var orderDetail: ArrayList<OrderDetailDomain>) : RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_item_order, parent, false)
        return ViewHolder(inflate)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var getDish = GetDish(orderDetail[position].IdDish.toString().toInt())

        getDish.getDishById { dish ->
            holder.tvName.text = dish.Name
            holder.tvPrice.text = "$" + dish.Price.toString()
            holder.tvNumber.text = "X" + orderDetail[position].Number.toString()
            val drawableResourceId = holder.itemView.context.resources.getIdentifier(
                dish.Image,"drawable", holder.itemView.context.packageName
            )
            Glide.with(holder.itemView.context).load(drawableResourceId).into(holder.pic)
        }

    }


    override fun getItemCount(): Int {
        return orderDetail.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNumber: TextView
        var tvPrice: TextView
        var tvName: TextView
        var pic: ImageView


        init {
            tvNumber = itemView.findViewById(R.id.tvNumber)
            tvPrice = itemView.findViewById(R.id.tvPrice)
            tvName = itemView.findViewById(R.id.tvName)
            pic = itemView.findViewById(R.id.img)
        }
    }
}