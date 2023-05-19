package com.example.myapplication.Database


import com.example.myapplication.Domain.DishDomain
import com.google.firebase.database.*

class GetCategory( var idCategory: Int) {

    private lateinit var dbReference: DatabaseReference

    fun getDishByCategory(callback: (ArrayList<DishDomain>) -> Unit) {
        val listDishPopular = ArrayList<DishDomain>()
        dbReference = FirebaseDatabase.getInstance().getReference("Dish")

        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listDishPopular.clear()
                if (snapshot.exists()) {
                    for (dish in snapshot.children) {
                        val dis = dish.getValue(DishDomain::class.java)
                        if (dis != null) {
                            if(dis.IdCategory==idCategory) {
                                listDishPopular.add(dis)
                            }
                        }

                    }
                }
                callback(listDishPopular)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value: ${error.toException()}")
            }
        })
    }

    fun getDishByCategorySortPopular(callback: (ArrayList<DishDomain>) -> Unit) {
        val listDishPopular = ArrayList<DishDomain>()
        dbReference = FirebaseDatabase.getInstance().getReference("Dish")

        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listDishPopular.clear()
                if (snapshot.exists()) {
                    for (dish in snapshot.children) {
                        val dis = dish.getValue(DishDomain::class.java)
                        if (dis != null) {
                            if(dis.IdCategory==idCategory) {
                                listDishPopular.add(dis)
                            }
                        }

                    }
                }
                listDishPopular.sortByDescending { dishDomain ->  dishDomain.Sold }
                callback(listDishPopular)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value: ${error.toException()}")
            }
        })
    }

    fun getDishByCategorySortPrice(callback: (ArrayList<DishDomain>) -> Unit) {
        val listDishPopular = ArrayList<DishDomain>()
        dbReference = FirebaseDatabase.getInstance().getReference("Dish")

        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listDishPopular.clear()
                if (snapshot.exists()) {
                    for (dish in snapshot.children) {
                        val dis = dish.getValue(DishDomain::class.java)
                        if (dis != null) {
                            if(dis.IdCategory==idCategory) {
                                listDishPopular.add(dis)
                            }
                        }

                    }
                }
                listDishPopular.sortByDescending { dishDomain ->  dishDomain.Price }
                callback(listDishPopular)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value: ${error.toException()}")
            }
        })
    }


}