package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Activity.CartActivity
import com.example.myapplication.Activity.DetailAccountActivity
import com.example.myapplication.Activity.SearchDishActivity
import com.example.myapplication.Adapter.CategoryAdapter
import com.example.myapplication.Adapter.PopularAdapter
import com.example.myapplication.Database.GetCart
import com.example.myapplication.Database.GetDataMain
import com.example.myapplication.Database.GetUser
import com.example.myapplication.Domain.DishDomain
import com.example.myapplication.Interface.ReLoadData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var idUser:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val i = intent
        if(i.getStringExtra("idUser")!=null) {
            idUser = i.getStringExtra("idUser").toString()
        } else
            idUser = "1"


        // set View cho Category
        recyclerViewCategory()
        // set View cho Popular
        recyclerViewPopular()
        // set envent cho button trong bottom

        processSearch()
        bottomNavigation()

        detailAccount()
    }

    @SuppressLint("SetTextI18n")
    private fun detailAccount() {
        val btnDetailAccount = findViewById<ImageView>(R.id.btnDetailAccount)

        val textView = findViewById<TextView>(R.id.tvStar)

        val getUser = GetUser()
        getUser.getUserByIdUser(idUser) {
            user -> textView.text = "Hi ${user.Name}"
        }

        btnDetailAccount.setOnClickListener {
            val intent = Intent(this@MainActivity, DetailAccountActivity::class.java)
            intent.putExtra("idUser", idUser)
            startActivity(intent)
        }
    }

    private fun processSearch() {
        val searchDish = findViewById<TextView>(R.id.searchDish)
        searchDish.setOnClickListener {
            val intent = Intent(this@MainActivity, SearchDishActivity::class.java)
            intent.putExtra("idUser", idUser)
            startActivity(intent)
        }
    }

    private fun bottomNavigation() {
        val floatingActionButton = findViewById<FloatingActionButton>(R.id.card_btn1)
        val homeBtn = findViewById<LinearLayout>(R.id.home_btn1)
        floatingActionButton.setOnClickListener {
            var intent = Intent(this@MainActivity, CartActivity::class.java)
            intent.putExtra("idUser", idUser)
            startActivity(intent)
        }
        homeBtn.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    MainActivity::class.java
                )
            )
        }
    }

    private fun recyclerViewPopular() {

        var recyclerViewPopularList = findViewById<RecyclerView>(R.id.recyclerView2)
        var data = GetDataMain()
        recyclerViewPopularList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        data.getDishPopular { list ->
//            giới hạn 5 phần tử
            var subList = getFirstFiveElements(list)
            var adt = PopularAdapter(idUser,subList, object :ReLoadData {
                
                override fun changed() {
                    
                }

                override fun addToCart(idDish: Int) {
                    val cart = GetCart(idUser)
                    cart.addCart(idDish, 1)
                    Toast.makeText(this@MainActivity, "The item has been added to cart", Toast.LENGTH_SHORT).show()
                }
            })
            recyclerViewPopularList.adapter = adt
        }

    }

    private fun recyclerViewCategory() {
        var recyclerViewCategoryList = findViewById<RecyclerView>(R.id.recyclerViewRecommended)
        recyclerViewCategoryList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var data = GetDataMain()
        data.getListCategory { list->
            var adt = CategoryAdapter(list)
            recyclerViewCategoryList.adapter = adt
        }
    }

    private fun getFirstFiveElements(list: ArrayList<DishDomain>): ArrayList<DishDomain> {
        return ArrayList(list.subList(0, 5))
    }

}
