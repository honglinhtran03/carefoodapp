package com.example.myapplication.Adapter

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.myapplication.R
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.myapplication.Activity.DetailDish
import com.example.myapplication.Domain.DishDomain
import com.example.myapplication.Interface.ReLoadData
import java.util.ArrayList

class PopularAdapter(var idUser:String,var foodDomains: ArrayList<DishDomain>, var reLoadData: ReLoadData) :
    RecyclerView.Adapter<PopularAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_popular, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tittle.text = foodDomains[position].Name
        holder.fee.text = foodDomains[position].Price.toString()
        //Cách lấy ảnh ở drawable, truyền vào tên của ảnh ở thư mục drawable
        val drawableRecouseId = holder.itemView.context.resources.getIdentifier(
            foodDomains[position].Image, "drawable",
            holder.itemView.context.packageName
        )
        Glide.with(holder.itemView.context).load(drawableRecouseId).into(holder.pic)
        holder.pic.setOnClickListener(View.OnClickListener {
            val intent = Intent(holder.itemView.context, DetailDish::class.java)
            intent.putExtra("idDish", foodDomains[position].Id)
            intent.putExtra("idUser", idUser)
            holder.itemView.context.startActivity(intent)
        })

        holder.addBtn.setOnClickListener(View.OnClickListener {
            reLoadData.addToCart(foodDomains[position].Id!!)
        })
    }

    override fun getItemCount(): Int {
        return foodDomains.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tittle: TextView
        var fee: TextView
        var pic: ImageView
        var addBtn: TextView

        init {
            tittle = itemView.findViewById(R.id.tittle)
            fee = itemView.findViewById(R.id.fee)
            pic = itemView.findViewById(R.id.pic)
            addBtn = itemView.findViewById(R.id.addBtn)
        }
    }
}