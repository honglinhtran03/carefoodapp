package com.example.myapplication.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.PopularAdapter
import com.example.myapplication.Database.GetCart
import com.example.myapplication.Database.GetCategory
import com.example.myapplication.Database.GetDataMain
import com.example.myapplication.Database.GetDish
import com.example.myapplication.Domain.DishDomain
import com.example.myapplication.Interface.ReLoadData
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_detail_dish.*

class DetailDish : AppCompatActivity() {

    private lateinit var btnMinus:TextView
    private lateinit var btnPlus:TextView
    private lateinit var tvNumber:TextView

    private lateinit var btnAddCart:ImageView

    private lateinit var idUser:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_dish)

        var i = intent
        if(i.getStringExtra("idUser")!=null) {
            idUser = i.getStringExtra("idUser").toString()
        } else
            idUser = "1"

        initView()
        getDataDish()
        onClickListener()
    }

    private fun initView() {
        btnPlus = findViewById(R.id.btnPLus)
        btnMinus = findViewById(R.id.btnMinus)
        tvNumber = findViewById(R.id.tvNumber)

        btnAddCart = findViewById(R.id.btnAddCart)



        btnMinus.setOnClickListener {
            var number = tvNumber.text.toString().toInt()
            if(number!=1) {
                number -= 1
                tvNumber.text = number.toString()
            }
        }
        btnPlus.setOnClickListener {
            var number = tvNumber.text.toString().toInt()
            number += 1
            tvNumber.text = number.toString()
        }


    }

    private fun getDataDish() {
        var i = intent
        var idDish = i.getIntExtra("idDish", 1)
        var getDish = GetDish(idDish)
        getDish.getDishById { dish->
            setDataDish(dish)
        }
        btnAddCart.setOnClickListener {
            val cart = GetCart(idUser)
            cart.addCart(idDish, tvNumber.text.toString().toInt())
        }
    }


    @SuppressLint("SetTextI18n")
    private fun setDataDish(dish: DishDomain) {
        var imgId = resources.getIdentifier(dish.Image, "drawable", this.packageName)
        imgDish.setImageResource(imgId)
        tvName.text = dish.Name
        tvTime.text = "${dish.Time.toString()} min"
        tvStar.text = dish.Star.toString()
        tvKcal.text = "${dish.Kcal.toString()}kcal"
        tvPrice.text = "$${dish.Price}"
        tvAbout.text = dish.Describe
        tvSold.text = dish.Sold.toString()

        recyclerViewRecommended(dish.IdCategory!!)
    }


    private fun onClickListener() {
//         btn back
        var btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            var intent = Intent(this@DetailDish, MainActivity::class.java)
            intent.putExtra("idUser", idUser)
            startActivity(intent)
        }
    }

    private fun recyclerViewRecommended(idCategory:Int) {

        var recyclerViewPopularList = findViewById<RecyclerView>(R.id.recyclerViewRecommended)

        recyclerViewPopularList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var data = GetCategory(idCategory)
        data.getDishByCategory { list ->
            var adt = PopularAdapter(idUser, list, object : ReLoadData {

                override fun changed() {

                }

                override fun addToCart(idDish: Int) {
                    val cart = GetCart(idUser)
                    cart.addCart(idDish, 1)
                    Toast.makeText(this@DetailDish, "The item has been added to cart", Toast.LENGTH_SHORT).show()
                }
            })
            recyclerViewPopularList.adapter = adt
        }

    }

}