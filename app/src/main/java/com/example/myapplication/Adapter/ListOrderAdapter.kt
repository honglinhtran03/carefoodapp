package com.example.myapplication.Adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Activity.StatusOrderActivity

import com.example.myapplication.Domain.OrderDomain
import com.example.myapplication.R
import kotlinx.android.synthetic.main.layout_item_order.view.*

class ListOrderAdapter(var idUser:String, var listOrder: ArrayList<OrderDomain>) : RecyclerView.Adapter<ListOrderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_item_order, parent, false)
        return ViewHolder(inflate)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var order = listOrder[position]
        holder.tvIdOrder.text = order.Id
        holder.tvAddress.text = order.Address
        holder.tvNumberPhone.text = order.NumberPhone

        when(order.Status) {
            1 -> holder.tvStatus.text = "Preparing"
            2 -> holder.tvStatus.text = "Transported"
            3 -> holder.tvStatus.text = "Arrived"
            else -> holder.tvStatus.text = "Preparing"
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, StatusOrderActivity::class.java)
            intent.putExtra("idOrder", order.Id)
            intent.putExtra("idUser", idUser)

            holder.itemView.context.startActivity(intent)

        }

    }


    override fun getItemCount(): Int {
        return listOrder.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvIdOrder: TextView
        var tvStatus: TextView
        var tvTotalPrice: TextView
        var tvAddress: TextView
        var tvNumberPhone: TextView


        init {
            tvIdOrder = itemView.findViewById(R.id.idOrder)
            tvStatus = itemView.findViewById(R.id.tvStatus)
            tvTotalPrice = itemView.findViewById(R.id.tvPrice)
            tvAddress = itemView.findViewById(R.id.tvAddress)
            tvNumberPhone = itemView.findViewById(R.id.tvNumberPhone)
        }
    }
}