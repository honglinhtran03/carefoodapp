package com.example.myapplication.Database

import com.example.myapplication.Domain.OrderDetailDomain
import com.example.myapplication.Domain.OrderDomain
import com.google.firebase.database.*

class GetOrder(var id:String) {

    private lateinit var dbReference: DatabaseReference

    fun getOrderById(callback: (OrderDomain) -> Unit) {
        dbReference = FirebaseDatabase.getInstance().getReference("Order")

        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (value in snapshot.children) {
                        val order = value.getValue(OrderDomain::class.java)
                        if (order != null) {
                            if(order.Id==id) {
                                callback(order)
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

    fun getOrderByIdUser(idUser:String, callback: (ArrayList<OrderDomain>) -> Unit) {
        val listOrder = ArrayList<OrderDomain>()
        dbReference = FirebaseDatabase.getInstance().getReference("Order")

        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listOrder.clear()
                if (snapshot.exists()) {
                    for (value in snapshot.children) {
                        val order = value.getValue(OrderDomain::class.java)
                        if (order != null) {
                            if(order.IdUser==idUser) {
                                listOrder.add(order)
                            }
                        }

                    }
                }
                callback(listOrder)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value: ${error.toException()}")
            }
        })
    }

    fun getOrderDetail(idOrder:String, callback: (ArrayList<OrderDetailDomain>) -> Unit) {
        val listCart = ArrayList<OrderDetailDomain>()
        dbReference = FirebaseDatabase.getInstance().getReference("Order Detail")

        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listCart.clear()
                if (snapshot.exists()) {
                    for (value in snapshot.children) {
                        val order = value.getValue(OrderDetailDomain::class.java)
                        if (order != null) {
                            if(order.IdOrder==idOrder) {
                                listCart.add(order)
                            }
                        }

                    }
                }
                callback(listCart)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value: ${error.toException()}")
            }
        })
    }

}