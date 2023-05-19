package com.example.myapplication.Database

import com.example.myapplication.Domain.CartDomain
import com.google.firebase.database.*

class GetCart(private val idUser: String) {
    private lateinit var dbReference: DatabaseReference

    fun getCartByIdUser(callback: (ArrayList<CartDomain>) -> Unit) {
        val listCart = ArrayList<CartDomain>()
        dbReference = FirebaseDatabase.getInstance().getReference("Cart")

        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listCart.clear()
                if (snapshot.exists()) {
                    for (value in snapshot.children) {
                        val cart = value.getValue(CartDomain::class.java)
                        if (cart != null) {
                            if(cart.IdUser==idUser) {
                                listCart.add(cart)
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

    fun getOrderByIdUser(callback: (ArrayList<CartDomain>) -> Unit) {
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
                                listCart.add(cart)
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


    fun addCart(idDish:Int, number:Int) {

        var foundCart = false

        dbReference = FirebaseDatabase.getInstance().getReference("Cart")
        dbReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (value in snapshot.children) {
                        val cart = value.getValue(CartDomain::class.java)
                        if (cart != null) {
                            if(cart.IdUser==idUser && cart.IdDish==idDish) {
                                dbReference.child(cart.Id).setValue(CartDomain(cart.Id, idUser, cart.IdDish, cart.Number+number))
                                foundCart = true
                            }
                        }
                    }
                    if (!foundCart) {
                        var dbr: DatabaseReference = FirebaseDatabase.getInstance().getReference("Cart")
                        val newCartId = dbr.push().key!!
                        var cart = CartDomain(newCartId,idUser,idDish,number)
                        dbr.child(newCartId).setValue(cart)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value: ${error.toException()}")
            }
        })
    }
}