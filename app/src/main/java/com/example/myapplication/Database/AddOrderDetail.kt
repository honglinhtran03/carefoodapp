package com.example.myapplication.Database

import com.example.myapplication.Domain.OrderDetailDomain
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddOrderDetail(var listOrder:ArrayList<OrderDetailDomain>) {
    private  var dbReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Order Detail")
    init {
        for (order in listOrder) {
            var id = dbReference.push().key!!
            dbReference.child(id).setValue(order)
        }
    }
}