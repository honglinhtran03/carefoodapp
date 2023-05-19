package com.example.myapplication.Database

import com.example.myapplication.Domain.DishDomain
import com.example.myapplication.Domain.OrderDetailDomain
import com.google.firebase.database.*

class Sold(var listOrder:ArrayList<OrderDetailDomain>) {

    private lateinit var dbReference: DatabaseReference

    init {
        for(order in listOrder) {
            updateSold(order.IdDish, order.Number)
        }
    }

    fun getSoldByIdDish(idDish:Int, callback: (Int) -> Unit) {

        dbReference = FirebaseDatabase.getInstance().getReference("Dish")

        dbReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (dish in snapshot.children) {
                        val dis = dish.getValue(DishDomain::class.java)
                        if (dis != null) {
                            if(dis.Id==idDish) {
                                callback(dis.Sold)
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

    private fun updateSold(idDish: Int, number:Int ) {

        dbReference = FirebaseDatabase.getInstance().getReference("Dish")

        dbReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (dish in snapshot.children) {
                        val dis = dish.getValue(DishDomain::class.java)
                        if (dis != null) {
                            if(dis.Id==idDish) {
                                getSoldByIdDish(idDish) { sold ->
                                    dis.Sold = sold + number
                                    dbReference.child(dis.Id.toString()).setValue(dis)
                                }
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