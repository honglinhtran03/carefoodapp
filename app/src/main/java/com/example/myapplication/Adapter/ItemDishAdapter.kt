package com.example.myapplication.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Activity.DetailDish
import com.example.myapplication.Domain.DishDomain
import com.example.myapplication.R

class ItemDishAdapter(var idUser:String, var listDish :ArrayList<DishDomain>) : RecyclerView.Adapter<ItemDishAdapter.ViewHolder>()

{

    interface DataChangedListener {
        fun onDataChanged()
    }

    var listener: DataChangedListener? = null


    // Phương thức này để cập nhật dữ liệu
    fun updateData(newListDish: ArrayList<DishDomain>) {
        this.listDish = newListDish
        notifyDataSetChanged()
        listener?.onDataChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_dish, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = listDish[position].Name
        holder.price.text = listDish[position].Price.toString()
        holder.sold.text = listDish[position].Sold.toString()
        //Cách lấy ảnh ở drawable, truyền vào tên của ảnh ở thư mục drawable
        val drawableRecouseId = holder.itemView.context.resources.getIdentifier(
            listDish[position].Image, "drawable",
            holder.itemView.context.packageName
        )
        Glide.with(holder.itemView.context).load(drawableRecouseId).into(holder.pic)
        holder.itemView.setOnClickListener {
            var intent = Intent(holder.itemView.context, DetailDish::class.java)
            intent.putExtra("idDish", listDish[position].Id)
            intent.putExtra("idUser", idUser)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listDish.size
    }

    fun setData(dataList: ArrayList<DishDomain>) {
        listDish = dataList
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var pic: ImageView
        var price: TextView
        var sold:TextView

        init {
            name = itemView.findViewById(R.id.tvName)
            pic = itemView.findViewById(R.id.imgDish)
            price = itemView.findViewById(R.id.tvPrice)
            sold = itemView.findViewById(R.id.tvSold)
        }

    }
}
