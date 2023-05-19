package com.example.myapplication.Database

import com.example.myapplication.Domain.Category
import com.example.myapplication.Domain.DishDomain
import com.google.firebase.database.*

class GetDataMain {
    private lateinit var dbReference: DatabaseReference

    private lateinit var listCategory:ArrayList<Category>
    private lateinit var listDishPopular:ArrayList<DishDomain>

//    Hàm get category
fun getListCategory(callback: (ArrayList<Category>) -> Unit) {
    val listCategory = ArrayList<Category>()
    val dbReference = FirebaseDatabase.getInstance().getReference("Category")

    dbReference.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            listCategory.clear()
            if (snapshot.exists()) {
                for (category in snapshot.children) {
                    val cate = category.getValue(Category::class.java)
                    listCategory.add(cate!!)
                }
            }
            callback(listCategory)
        }

        override fun onCancelled(error: DatabaseError) {
            println("Failed to read value: ${error.toException()}")
        }
    })
}

//    Hàm get dish Popular
fun getDishPopular(callback: (ArrayList<DishDomain>) -> Unit) {
    val listDishPopular = ArrayList<DishDomain>()
    val dbReference = FirebaseDatabase.getInstance().getReference("Dish")

    dbReference.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            listDishPopular.clear()
            if (snapshot.exists()) {
                for (dish in snapshot.children) {
                    val dis = dish.getValue(DishDomain::class.java)
                    listDishPopular.add(dis!!)
                }
            }
//            sắp xếp sold giảm dần
            listDishPopular.sortByDescending { dishDomain ->  dishDomain.Sold }
            callback(listDishPopular)
        }

        override fun onCancelled(error: DatabaseError) {
            println("Failed to read value: ${error.toException()}")
        }
    })
}

}
