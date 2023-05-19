package com.example.myapplication.Database

import com.example.myapplication.Domain.DishDomain
import com.google.firebase.database.*

class GetDish(var idDish: Int) {

    private lateinit var dbReference: DatabaseReference

    fun getDishById(callback: (DishDomain) -> Unit) {

        dbReference = FirebaseDatabase.getInstance().getReference("Dish")

        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (dish in snapshot.children) {
                        val dis = dish.getValue(DishDomain::class.java)
                        if (dis != null) {
                            if(dis.Id==idDish) {
                                callback(dis)
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value: ${error.toException()}")
            }
        })
    }

    fun getDishByIdForCart(callback: (DishDomain) -> Unit) {

        dbReference = FirebaseDatabase.getInstance().getReference("Dish")

        dbReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (dish in snapshot.children) {
                        val dis = dish.getValue(DishDomain::class.java)
                        if (dis != null) {
                            if(dis.Id==idDish) {
                                callback(dis)
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value: ${error.toException()}")
            }
        })
    }
}