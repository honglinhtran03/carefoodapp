package com.example.myapplication.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.Domain.AccountDomain
import com.example.myapplication.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    
    lateinit var edtEmail:EditText
    lateinit var edtName:EditText
    lateinit var edtPass:EditText
    lateinit var edtRePass:EditText
    
    lateinit var btnSignUp:Button
    
    lateinit var btnConvert:TextView

    lateinit var dbReference: DatabaseReference
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        
        initView()


        btnSignUp.setOnClickListener {
            processSignUp()
        }
        
        convertLogin()

        
    }

    private fun convertLogin() {
        btnConvert.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
        }
    }

    private fun processSignUp() {
        if (edtEmail.text.toString() !="" || edtName.text.toString() !="" || edtPass.text.toString()!="" || edtRePass.text.toString()!="") {
            
            if (edtPass.text.toString()==edtRePass.text.toString()) {

                dbReference = FirebaseDatabase.getInstance().getReference("Account")
                var idAccount = dbReference.push().key!!
                var account = AccountDomain(idAccount,edtEmail.text.toString(), edtPass.text.toString(), edtName.text.toString() )
                dbReference.child(idAccount).setValue(account)
                edtName.setText("")
                edtEmail.setText("")
                edtPass.setText("")
                edtRePass.setText("")
                Toast.makeText(this@SignUpActivity, "Sign up succsess", Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(this@SignUpActivity, "Passwords do not match", Toast.LENGTH_SHORT).show()
            
        } else
            Toast.makeText(this@SignUpActivity, "Information cannot be left blank", Toast.LENGTH_SHORT).show()
    }

    private fun initView() {
        edtEmail = findViewById(R.id.edtEmail)
        edtName = findViewById(R.id.edtName)
        edtPass = findViewById(R.id.edtPass)
        edtRePass = findViewById(R.id.edtRePass)
        
        btnSignUp = findViewById(R.id.btnSignUp)
        btnConvert = findViewById(R.id.btnConvertLogIn)
    }
}