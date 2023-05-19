package com.example.myapplication.Database

import com.example.myapplication.Domain.CartDomain
import com.google.firebase.database.*

class DeleteCart(private val idUser: String) {
    private var dbReference: DatabaseReference

    init {
        val listCart = ArrayList<CartDomain>()
        dbReference = FirebaseDatabase.getInstance().getReference("Cart")

        dbReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listCart.clear()
                if (snapshot.exists()) {
                    for (value in snapshot.children) {
                        val cart = value.getValue(CartDomain::class.java)
                        if (cart != null) {
                            if(cart.IdUser==idUser) {
                                dbReference.child(cart.Id).removeValue()
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