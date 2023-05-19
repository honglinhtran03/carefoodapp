package com.example.projectfood.Adapter

import com.example.projectfood.Domain.FoodDomain
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.carefood.R
import com.bumptech.glide.Glide
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import java.util.ArrayList

class PopularAdapter(var foodDomains: ArrayList<FoodDomain>) :
    RecyclerView.Adapter<PopularAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_popular, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tittle.text = foodDomains.get(position).title
        holder.fee.text = foodDomains.get(position).fee.toString()
        //Cách lấy ảnh ở drawable, truyền vào tên của ảnh ở thư mục drawable
        val drawableRecouseId = holder.itemView.context.resources.getIdentifier(
            foodDomains[position].pic, "drawable",
            holder.itemView.context.packageName
        )
        Glide.with(holder.itemView.context).load(drawableRecouseId).into(holder.pic)
//        holder.addBtn.setOnClickListener(View.OnClickListener {
//            val intent = Intent(holder.itemView.context, ShowDetailActivity::class.java)
//            intent.putExtra("object", foodDomains[position])
//            holder.itemView.context.startActivity(intent)
//        })
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