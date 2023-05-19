package com.example.myapplication.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.Domain.DishDomain
import com.example.myapplication.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddData : AppCompatActivity() {
    private lateinit var dbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        dbRef = FirebaseDatabase.getInstance().getReference("Dish")

//        button.setOnClickListener {
//            var t1 = edt1.text.toString().toInt()
//            var t2 = edt2.text.toString()
//            var t3 = edt3.text.toString().toDouble()
//            var t4 = edt4.text.toString()
//            var t5 = edt5.text.toString().toInt()
//            var t6 = edt6.text.toString().toDouble()
//            var t7 = edt7.text.toString().toInt()
//            var t8 = edt8.text.toString()
//
//            for(i in 1..11) {
//                var dish = DishDomain(i, t2,t3,t4,t5,t6,t7,t8)
//                dbRef.child(i.toString()).setValue(dish)
//        }

        }

}