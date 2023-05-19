package com.example.myapplication.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.ListOrderAdapter
import com.example.myapplication.Database.GetOrder
import com.example.myapplication.R

class MyOrderActivity : AppCompatActivity() {

    private lateinit var idUser:String

    private lateinit var recyclerViewMyOrder: RecyclerView

    private lateinit var btnBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_order)

        val i = intent
        if(i.getStringExtra("idUser")!=null) {
            idUser = i.getStringExtra("idUser").toString()
        } else
            idUser = "1"

        initView()

        setView()

    }

    private fun setView() {
        recyclerViewMyOrder.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        var getOrder = GetOrder("1")
        getOrder.getOrderByIdUser(idUser) { listOrder ->
            recyclerViewMyOrder.adapter = ListOrderAdapter(idUser, listOrder)
        }

        btnBack.setOnClickListener {
            val intent = Intent(this@MyOrderActivity, DetailAccountActivity::class.java)
            intent.putExtra("idUser", idUser)
            startActivity(intent)
        }
    }

    private fun initView() {
        recyclerViewMyOrder = findViewById(R.id.recyclerViewMyOrder)
        btnBack = findViewById(R.id.btnBack)
    }
}