package com.example.myapplication.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.Domain.AccountDomain
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.google.firebase.database.*



class LoginActivity : AppCompatActivity() {

    lateinit var edtEmail:EditText
    lateinit var edtPass:EditText

    lateinit var btnLogin:Button

    lateinit var dbReference: DatabaseReference


    private val RC_SIGN_IN = 123

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        initView()

        convertSignUp()

//        loginWithGoogle()

        btnLogin.setOnClickListener {
            processLogin(edtEmail.text.toString(), edtPass.text.toString())
        }

    }

//    private fun loginWithGoogle() {
//        var btnLoginWithGoogle = findViewById<LinearLayout>(R.id.loginWithGoogle)
//        firebaseAuth = FirebaseAuth.getInstance()
//        btnLoginWithGoogle.setOnClickListener {
//            // Xử lý sự kiện khi người dùng bấm nút đăng nhập với Google
//            val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken("373764605967-rr2pc9igr3ca5nkro01bbbsuorrhqb3r.apps.googleusercontent.com")
//                .requestEmail()
//                .build()
//
//            val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
//
//            val signInIntent = googleSignInClient.signInIntent
//            startActivityForResult(signInIntent, RC_SIGN_IN)
//
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                val account = task.getResult(ApiException::class.java)
//                firebaseAuthWithGoogle(account.idToken!!)
//            } catch (e: ApiException) {
//                // Xử lý lỗi
//            }
//        }
//    }
//
//    private fun firebaseAuthWithGoogle(idToken: String) {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        firebaseAuth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Đăng nhập thành công
//                     Toast.makeText(this@LoginActivity, "no fail", Toast.LENGTH_SHORT).show()
//                    val user = firebaseAuth.currentUser
//
//                    if(user!=null) {
//                        var email = user.email
//                        var name = user.displayName
//                        var id = user.uid
//                            if (email != null && name !=null && id!=null) {
//                                var account = AccountDomain(id, email, "", name)
//                                dbReference = FirebaseDatabase.getInstance().getReference("Account")
//
//                                dbReference.child(id).setValue(account)
//
//                            }
//                    }
//
//                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                    startActivity(intent)
//
//                    // Tiếp tục xử lý sau khi đăng nhập thành công
//                } else {
//                    // Xử lý lỗi đăng nhập
//                    Toast.makeText(this@LoginActivity, "fail", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }



    private fun convertSignUp() {
        var btnConvertSignUp = findViewById<TextView>(R.id.btnConvertSignUp)
        btnConvertSignUp.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
        }
    }


    private fun initView() {
        edtEmail = findViewById(R.id.edtEmail)
        edtPass = findViewById(R.id.edtPass)

        btnLogin = findViewById(R.id.btnLogin)
    }

    private fun processLogin(email: String, pass: String) {

        dbReference = FirebaseDatabase.getInstance().getReference("Account")

        var isLoginSuccessful = false

        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (value in snapshot.children) {
                        val account = value.getValue(AccountDomain::class.java)
                        if (account != null) {
                            if(account.Email==email&& account.Password==pass) {
                                var intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.putExtra("idUser", account.Id)
                                startActivity(intent)
                                isLoginSuccessful = true
                                break
                            }
                        }
                    }
                    if(!isLoginSuccessful) {
                        Toast.makeText(this@LoginActivity, "Incorrect email or password", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value: ${error.toException()}")
            }
        })

    }

}