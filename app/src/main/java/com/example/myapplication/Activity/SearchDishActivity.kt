package com.example.myapplication.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.ItemDishAdapter
import com.example.myapplication.Database.GetDataMain
import com.example.myapplication.Domain.DishDomain
import com.example.myapplication.MainActivity
import com.example.myapplication.R

class SearchDishActivity : AppCompatActivity() {

    lateinit var searchView: SearchView
    lateinit var recyclerView: RecyclerView

    lateinit var btnBack:ImageView

    lateinit var adt: ItemDishAdapter

    private lateinit var idUser:String


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_dish)

        var i = intent
        if(i.getStringExtra("idUser")!=null) {
            idUser = i.getStringExtra("idUser").toString()
        } else
            idUser = "1"

        // Load animation
        val animation = AnimationUtils.loadAnimation(this, R.transition.transition)

        // Apply animation to root layout
        val rootView = findViewById<View>(android.R.id.content)
        rootView.startAnimation(animation)

        getRecyclerView()
        getData()
        searchViewEvent()

        backActivity()

    }

    private fun backActivity() {
        btnBack = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            val intent = Intent(this@SearchDishActivity, MainActivity::class.java)
            intent.putExtra("idUser", idUser)
            startActivity(intent)
        }
    }

    private fun getData() {
        var getListDish = GetDataMain()
        getListDish.getDishPopular { listAll ->
            adt = ItemDishAdapter(idUser, listAll)
            recyclerView.adapter = adt
        }
    }

    private fun getRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewFilter)
        searchView = findViewById(R.id.search_view)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun searchViewEvent() {

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                filterList(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {

                return false

            }

        })
    }

    private fun filterList(query: String) {
        var listDishFilter = ArrayList<DishDomain>()
        var list = GetDataMain()
        list.getDishPopular { listDishAll ->
            for (itemDish in listDishAll) {
                if (itemDish.Name != null) {
                    if (itemDish.Name!!.uppercase().contains(query.uppercase())) {
                        listDishFilter.add(itemDish)
                    }
                }

            }

            if (listDishFilter.isEmpty()) {
                adt.updateData(listDishFilter)
                Toast.makeText(this@SearchDishActivity, "No data found", Toast.LENGTH_SHORT).show()
            } else {
                adt.updateData(listDishFilter)
            }
        }
    }


}