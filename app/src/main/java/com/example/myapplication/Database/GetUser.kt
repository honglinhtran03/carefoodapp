package com.example.myapplication.Database

import com.example.myapplication.Domain.AccountDomain
import com.google.firebase.database.*

class GetUser() {
    private lateinit var dbReference: DatabaseReference
    fun getUserByIdUser(idUser:String, callback: (AccountDomain) -> Unit) {
        dbReference = FirebaseDatabase.getInstance().getReference("Account")

        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (value in snapshot.children) {
                        val user = value.getValue(AccountDomain::class.java)
                        if (user != null) {
                            if(user.Id==idUser) {
                                callback(user)
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