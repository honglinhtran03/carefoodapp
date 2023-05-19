package com.example.myapplication.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.CartAdapter
import com.example.myapplication.Database.GetCart
import com.example.myapplication.Database.GetDish
import com.example.myapplication.Domain.CartDomain
import com.example.myapplication.Interface.ReLoadData
import com.example.myapplication.MainActivity
import com.example.myapplication.R

class CartActivity : AppCompatActivity() {

    lateinit var totalTxt:TextView
    lateinit var btnCheckOut:TextView
    lateinit var btnBack:ImageView

    private lateinit var idUser:String

    var adapter: RecyclerView.Adapter<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        var i = intent
        if(i.getStringExtra("idUser")!=null) {
            idUser = i.getStringExtra("idUser").toString()
        } else
            idUser = "1"

        initView()
        initList()

        btnCheckOut.setOnClickListener {
            var intent = Intent(this@CartActivity, CheckOutActivity::class.java)
            intent.putExtra("idUser", idUser)
            startActivity(intent)
        }
        btnBack.setOnClickListener {
            var intent = Intent(this@CartActivity, MainActivity::class.java)
            intent.putExtra("idUser", idUser)
            startActivity(intent)
        }

    }

    private fun initList() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val recyclerViewCart = findViewById<RecyclerView>(R.id.recyclerviewcart)
        recyclerViewCart.layoutManager = linearLayoutManager


        var getCart = GetCart(idUser)

        getCart.getCartByIdUser { listCart->
            var adapter = CartAdapter(listCart)
            recyclerViewCart.adapter = adapter
            setDataTextView(listCart)
        }

        findViewById<TextView>(R.id.emptyTxt).visibility = View.GONE
        findViewById<ScrollView>(R.id.scrollView4).visibility = View.VISIBLE
    }

    private fun initView() {
        totalTxt = findViewById(R.id.totalTxt)
        btnCheckOut = findViewById(R.id.btnCheckOut)
        btnBack = findViewById(R.id.btnBack)

    }

    private fun getTotalItem(cartDomain: ArrayList<CartDomain>, callback: (Double) -> Unit) {
        var total = 0.0
        var counter = 0
        for (item in cartDomain) {
            var listDish = GetDish(item.IdDish)
            listDish.getDishById { dish ->
                total += Math.round(dish.Price * item.Number * 100.0) / 100.0
                counter++
                if (counter == cartDomain.size) {
                    callback(total)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDataTextView(cart:ArrayList<CartDomain>) {
        getTotalItem(cart) { total ->
            totalTxt.text =  "$" + (Math.round(total  *100.0)/100.0).toString()
        }
    }

}