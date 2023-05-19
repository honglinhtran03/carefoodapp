package com.example.myapplication.Database

import com.example.myapplication.Domain.CartDomain
import com.google.firebase.database.FirebaseDatabase

class UpdateCart(var Id: String,var IdDish:Int, var IdUser:String ,var Number:Int, var type:String ) {
    var databaseReference = FirebaseDatabase.getInstance().getReference("Cart").child(Id)
    init {
        init()
    }

    private fun init() {
        if(type=="add") {
            var newNumber = Number + 1;
            var cart = CartDomain(Id, IdUser, IdDish, newNumber)
            databaseReference.setValue(cart)

        } else
        {
            var newNumber = Number - 1
            if(newNumber==0) {
                databaseReference.removeValue()
            } else {
                var cart = CartDomain(Id, IdUser, IdDish, newNumber)
                databaseReference.setValue(cart)
            }
        }
    }
}