package com.example.myapplication.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.Database.GetUser
import com.example.myapplication.Domain.AccountDomain
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.google.firebase.database.FirebaseDatabase

class DetailAccountActivity : AppCompatActivity() {

    private lateinit var idUser:String

    lateinit var btnBack:ImageView
    lateinit var linearLayoutMyOrder: LinearLayout
    lateinit var linearLayoutLogOut: LinearLayout
    lateinit var linearLayoutAddress: LinearLayout
    lateinit var linearLayoutNumberPhone: LinearLayout

    lateinit var tvNameUser:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_account)

        var i = intent
        if(i.getStringExtra("idUser")!=null) {
            idUser = i.getStringExtra("idUser").toString()
        } else
            idUser = "1"

        initView()


    }

    private fun initView() {
        btnBack = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            var intent = Intent(this@DetailAccountActivity, MainActivity::class.java)
            intent.putExtra("idUser", idUser)
            startActivity(intent)
        }
        linearLayoutLogOut = findViewById(R.id.linerLayoutLogOut)
        linearLayoutAddress = findViewById(R.id.linerLayoutAddress)
        linearLayoutNumberPhone = findViewById(R.id.linerLayoutPhoneNumber)
        linearLayoutMyOrder = findViewById(R.id.linerLayoutMyOrder)

//        my order
        linearLayoutMyOrder.setOnClickListener {
            var intent = Intent(this@DetailAccountActivity, MyOrderActivity::class.java)
            intent.putExtra("idUser", idUser)
            startActivity(intent)
        }
//        add address-number phone
        linearLayoutAddress.setOnClickListener {
            showDiaLogSelectLocation()
        }

        linearLayoutLogOut.setOnClickListener {
            startActivity(Intent(this@DetailAccountActivity, LoginActivity::class.java))
        }

        tvNameUser = findViewById(R.id.tvNameUser)
        val getUser = GetUser()
        getUser.getUserByIdUser(idUser) { user ->
            tvNameUser.text = user.Name
        }

    }

    private fun showDiaLogSelectLocation() {

        val build = AlertDialog.Builder(this@DetailAccountActivity)
        val view = layoutInflater.inflate(R.layout.dialog_adress, null)
        build.setView(view)

        val btnClose = view.findViewById<ImageView>(R.id.btnClose)
        val edtAddress = view.findViewById<EditText>(R.id.edtAddress)
        val edtPhone = view.findViewById<EditText>(R.id.edtPhoneNumber)
        val btnConfirm = view.findViewById<Button>(R.id.btnConfirm)

        val dialog:AlertDialog = build.create()
        dialog.show()


        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        btnConfirm.setOnClickListener {
            if (edtPhone.text.equals("") || edtAddress.text.equals("")) {
                Toast.makeText(this@DetailAccountActivity, "Address or phone number cannot be left blank", Toast.LENGTH_SHORT).show()
            } else {
                val databaseReference = FirebaseDatabase.getInstance().getReference("Account")
                val getUser = GetUser()
                getUser.getUserByIdUser(idUser) { user ->
                    val u = AccountDomain(idUser, user.Email, user.Password, user.Name ,edtAddress.text.toString(), edtPhone.text.toString() )
                    databaseReference.child(idUser).setValue(u)
                }
                dialog.dismiss()
            }
        }
    }

    private fun showDiaLogChangePassword() {

        val build = AlertDialog.Builder(this@DetailAccountActivity)
        val view = layoutInflater.inflate(R.layout.dialog_changepassword, null)
        build.setView(view)

        val btnClose = view.findViewById<ImageView>(R.id.btnClose)
        val edtOldPass = view.findViewById<EditText>(R.id.edtOldPass)
        val edtNewPass = view.findViewById<EditText>(R.id.edtNewPass)
        val edtRePass = view.findViewById<EditText>(R.id.edtRePass)
        val btnConfirm = view.findViewById<Button>(R.id.btnConfirm)

        val dialog:AlertDialog = build.create()
        dialog.show()


        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        btnConfirm.setOnClickListener {
            if (edtOldPass.text.equals("") || edtNewPass.text.equals("") || edtRePass.text.equals("")) {
                Toast.makeText(this@DetailAccountActivity, "Information cannot be left blank", Toast.LENGTH_SHORT).show()
            } else {
                val databaseReference = FirebaseDatabase.getInstance().getReference("Account")
                val getUser = GetUser()
                getUser.getUserByIdUser(idUser) { user ->
                    
                    if (edtOldPass.text.toString()==user.Password) {
                        val u = AccountDomain(idUser, user.Email, edtNewPass.text.toString(), user.Name ,user.Address, user.NumberPhone )
                        databaseReference.child(idUser).setValue(u)
                        dialog.dismiss()
                    }
                    else {
                        Toast.makeText(this@DetailAccountActivity, "old password is incorrect", Toast.LENGTH_SHORT).show()
                    }
                    
                    
                }
                
            }
        }
    }
}