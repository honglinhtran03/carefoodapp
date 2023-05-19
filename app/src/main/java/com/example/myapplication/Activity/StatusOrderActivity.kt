package com.example.myapplication.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.OrderDetailAdapter
import com.example.myapplication.Database.GetDish
import com.example.myapplication.Database.GetOrder
import com.example.myapplication.Domain.OrderDetailDomain
import com.example.myapplication.MainActivity
import com.example.myapplication.R

class StatusOrderActivity : AppCompatActivity() {

    lateinit var recyclerViewDishOrder: RecyclerView

    lateinit var logo:ImageView
    lateinit var tvTitle:TextView
    lateinit var tvStatus1:TextView
    lateinit var tvStatus2:TextView
    lateinit var tvStatus3:TextView

    lateinit var tvPrice:TextView
    lateinit var tvNumberItem:TextView
    lateinit var tvTotalPrice:TextView

    lateinit var tvAddress:TextView
    lateinit var tvNumberPhone:TextView

    lateinit var btnBack:ImageView

    private lateinit var idUser:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status_order)

        var i = intent
        if(i.getStringExtra("idUser")!=null) {
            idUser = i.getStringExtra("idUser").toString()
        } else
            idUser = "1"

        initView()

        setRecyclerDishOrder()

        setEventListener()
    }

    private fun setEventListener() {
        btnBack.setOnClickListener {
            var intent = Intent(this@StatusOrderActivity, MainActivity::class.java)
            intent.putExtra("idUser", idUser)
            startActivity(intent)
        }
    }

    private fun initView() {

        btnBack = findViewById(R.id.btnBack)

        recyclerViewDishOrder = findViewById(R.id.recyclerViewDishOrder)
        logo = findViewById(R.id.logo)
        tvTitle = findViewById(R.id.tvTitle)
        tvStatus1 = findViewById(R.id.tvStatus1)
        tvStatus2 = findViewById(R.id.tvStatus2)
        tvStatus3 = findViewById(R.id.tvStatus3)

        tvPrice = findViewById(R.id.tvPrice)
        tvNumberItem = findViewById(R.id.tvNumberItem)
        tvTotalPrice = findViewById(R.id.tvTotalCost)

        tvAddress = findViewById(R.id.tvAddress)
        tvNumberPhone = findViewById(R.id.tvNumberPhone)
    }

    private fun setRecyclerDishOrder() {


        recyclerViewDishOrder.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        Truyền id của order
        var i = intent
        var idOrder = i.getStringExtra("idOrder")
        if(idOrder!=null) {
            var getOrder = GetOrder(idOrder)
            getOrder.getOrderById { order ->

//          set Status
                setStatus(order.Status)
//          set Address
                tvAddress.text = order.Address
//          set NumberPhone
                tvNumberPhone.text = order.NumberPhone

                getOrder.getOrderDetail(order.Id) { listDetailOrder ->
                    recyclerViewDishOrder.adapter = OrderDetailAdapter(listDetailOrder)
                    setDataCost(listDetailOrder)
                }
            }
        }
    }

    private fun setStatus(status:Int) {

        val black = ContextCompat.getColor(this@StatusOrderActivity, R.color.black)

        if(status==1) {
            logo.setImageResource(R.drawable.logo_chef)
            tvTitle.text = "The restaurant is preparing your food."
            tvStatus1.setTextColor(black)
        } else if(status==2) {
            logo.setImageResource(R.drawable.logo_deliver)
            tvTitle.text = "Shipper is delivering to you."
            tvStatus2.setTextColor(black)
        } else {
            logo.setImageResource(R.drawable.logo_order1)
            tvTitle.text = "Your order has arrived"
            tvStatus3.setTextColor(black)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setDataCost(listOrderDetai: ArrayList<OrderDetailDomain>) {
        var number = 0
        var totalPrice = 0.0
        var count = 0
        for(orderDetail in listOrderDetai) {
            var dish = GetDish(orderDetail.IdDish)
            dish.getDishById { dish ->
                number += orderDetail.Number
                totalPrice += dish.Price * orderDetail.Number
                count++
                if(count==listOrderDetai.size) {
                    totalPrice = Math.round(totalPrice * 100.0) / 100.0
                    tvPrice.text = "$ $totalPrice"
                    tvNumberItem.text = "Total order($number item)"
                    tvTotalPrice.text = "$ ${totalPrice+10.0}"
                }
            }
        }
    }
}