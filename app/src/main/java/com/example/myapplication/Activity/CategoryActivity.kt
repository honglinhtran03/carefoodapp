package com.example.myapplication.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.ItemDishAdapter
import com.example.myapplication.Database.GetCategory
import com.example.myapplication.MainActivity
import com.example.myapplication.R

class CategoryActivity : AppCompatActivity() {

    lateinit var spinner:Spinner

    lateinit var recyclerView: RecyclerView

    private lateinit var idUser:String

    lateinit var btnBack: ImageView
    lateinit var btnSearch: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        var i = intent
        if(i.getStringExtra("idUser")!=null) {
            idUser = i.getStringExtra("idUser").toString()
        } else
            idUser = "1"

        btnClickListener()

        getIdCategory()
        setDataSpinner()

    }

    private fun btnClickListener() {
        btnBack = findViewById(R.id.btnBack)
        btnSearch = findViewById(R.id.btnSearch)

        btnBack.setOnClickListener {
            startActivity(Intent(this@CategoryActivity, MainActivity::class.java))
        }
    }

    private fun setDataSpinner() {
        spinner = findViewById(R.id.spiner)
        var list = listOf<String>("sort by", "popular", "rate", "price")
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list )

        recyclerView = findViewById(R.id.recyclerViewItemDish)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        var idCategory = getIdCategory()

        val getCategory = GetCategory(idCategory)
        getCategory.getDishByCategory { category ->
            val originalList = category // Danh sách gốc từ GetCategory
            var dataList = originalList // Danh sách dữ liệu hiện tại cho Adapter

            val itemDishAdapter = ItemDishAdapter(idUser, dataList)
            recyclerView.adapter = itemDishAdapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedItem = parent?.getItemAtPosition(position).toString()
                    if (selectedItem == "popular") {
                        // Xử lý sự kiện khi người dùng chọn "popular"
                        // Thay đổi danh sách dữ liệu cho Adapter và cập nhật giao diện
                        getCategory.getDishByCategorySortPopular { listDish ->
                            dataList = listDish
                            itemDishAdapter.setData(dataList)
                            itemDishAdapter.notifyDataSetChanged()
                        }
                    }

                    if (selectedItem == "price") {
                        // Xử lý sự kiện khi người dùng chọn "popular"
                        // Thay đổi danh sách dữ liệu cho Adapter và cập nhật giao diện
                        getCategory.getDishByCategorySortPrice { listDish ->
                            dataList = listDish
                            itemDishAdapter.setData(dataList)
                            itemDishAdapter.notifyDataSetChanged()
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Xử lý sự kiện khi không có mục nào được chọn
                }
            }
        }
    }

    private fun getIdCategory(): Int {
        var i = intent
        return i.getLongExtra("idCategory", 1).toInt()
    }

    private fun setDataRecyclerViewItemDish(idCategory: Int) {

        var getCategory = GetCategory( idCategory)
        getCategory.getDishByCategory { category ->
            var adt = ItemDishAdapter( idUser, category)
            recyclerView.adapter = adt
        }
    }
}