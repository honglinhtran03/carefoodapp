package com.example.myapplication.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.OrderAdapter
import com.example.myapplication.Database.*
import com.example.myapplication.Domain.OrderDetailDomain
import com.example.myapplication.Domain.OrderDomain
import com.example.myapplication.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CheckOutActivity : AppCompatActivity() {

    lateinit var btnOrder:ConstraintLayout

    lateinit var dialog: AlertDialog
    lateinit var tvAddress:TextView
    lateinit var tvNumberPhone:TextView

    lateinit var tvPrice:TextView
    lateinit var tvNumberItem:TextView
    lateinit var tvTotalPrice:TextView
    lateinit var btnBack:ImageView

    private lateinit var idUser:String

    private lateinit var dbRef : DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        var i = intent
        if(i.getStringExtra("idUser")!=null) {
            idUser = i.getStringExtra("idUser").toString()
        } else
            idUser = "1"

        initList()

        setRecyclerViewOrder()
        showDiaLogSelectLocation()
        proceedToOrder()

        setTextView()

    }

    private fun initList() {
        tvAddress = findViewById(R.id.tvAddress)
        tvNumberPhone = findViewById(R.id.tvNumberPhone)
        btnOrder = findViewById(R.id.btnOrder)

        tvPrice = findViewById(R.id.tvPrice)
        tvNumberItem = findViewById(R.id.tvNumberItem)
        tvTotalPrice = findViewById(R.id.tvTotalPrice)
        btnBack = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            var intent = Intent(this@CheckOutActivity, CartActivity::class.java)
            intent.putExtra("idUser", idUser)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun proceedToOrder() {
        btnOrder.setOnClickListener {

            dbRef = FirebaseDatabase.getInstance().getReference("Order")
            var idOrder = dbRef.push().key!!
            var address = tvAddress.text.toString()
            var number = tvNumberPhone.text.toString()
            var dateTime = getCurrentTimeFormatted()

            var orderDomain = OrderDomain(idOrder,idUser,1, address, number, dateTime)
//          Tạo ra một order
            dbRef.child(idOrder).setValue(orderDomain)

            var cart = GetCart(idUser)
            var listOrder = ArrayList<OrderDetailDomain>()
            cart.getOrderByIdUser { listCart->
                for(cart in listCart) {
//                   Tạo một list detail order từ cart
                    var order = OrderDetailDomain(idOrder, cart.IdDish, cart.Number )
                    listOrder.add(order)
                }
//                Thêm dữ liệu vào bảng detail order
                AddOrderDetail(listOrder)
                //                Update number sold
                Sold(listOrder)
//          Xóa các sản phẩm trong cart khi order
            }

            DeleteCart(idUser)
            val intent = Intent(this@CheckOutActivity, StatusOrderActivity::class.java)
            intent.putExtra("idOrder", idOrder)
            intent.putExtra("idUser", idUser)
            startActivity(intent)
        }
    }

    private fun showDiaLogSelectLocation() {
        val selectLocation = findViewById<LinearLayout>(R.id.select_location)
        selectLocation.setOnClickListener {
            val build = AlertDialog.Builder(this@CheckOutActivity)
            val view = layoutInflater.inflate(R.layout.dialog_adress, null)
            build.setView(view)

            val btnClose = view.findViewById<ImageView>(R.id.btnClose)
            val edtAddress = view.findViewById<EditText>(R.id.edtAddress)
            val edtPhone = view.findViewById<EditText>(R.id.edtPhoneNumber)
            val btnConfirm = view.findViewById<Button>(R.id.btnConfirm)
            
            dialog = build.create()
            dialog.show()
            
            
            btnClose.setOnClickListener {
                dialog.dismiss()
            }
            
            btnConfirm.setOnClickListener { 
                if (edtPhone.text.equals("") || edtAddress.text.equals("")) {
                    Toast.makeText(this@CheckOutActivity, "Address or phone number cannot be left blank", Toast.LENGTH_SHORT).show()
                } else {
                    tvAddress.text =  edtAddress.text
                    tvNumberPhone.text =  edtPhone.text
                    dialog.dismiss()
                }
            }
        }
    }

    private fun setRecyclerViewOrder() {
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerViewOrder)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        this@CheckOutActivity.runOnUiThread(Runnable {
            var order = GetCart(idUser)
            order.getCartByIdUser { order ->
                recyclerView.adapter = OrderAdapter(order)
            }
        })

    }

    @SuppressLint("SetTextI18n")
    private fun setTextView() {

        var number = 0
        var price = 0.0

        var couter = 0

        var order = GetCart(idUser)
        order.getOrderByIdUser { listOrder ->
            for(order in listOrder) {
                var dish = GetDish(order.IdDish)
                dish.getDishById { dis  ->
                    number += order.Number
                    price += dis.Price * order.Number
                    couter++
                    if (couter==listOrder.size) {
                        price = Math.round(price * 100.0) / 100.0
                        tvPrice.text = "$$price"
                        tvNumberItem.text = "Total order($number item)"
                        tvTotalPrice.text = "$" + (price+10.0).toString()
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentTimeFormatted(): String {
        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy") // Định dạng theo giờ, phút, ngày, tháng, năm
        return currentTime.format(formatter)
    }
}