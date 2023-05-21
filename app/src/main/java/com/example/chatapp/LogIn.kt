package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LogIn : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignup: Button

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        //hide actionbar
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edit_email)
        edtPass = findViewById(R.id.edit_pass)
        btnLogin = findViewById(R.id.btnSignIn)
        btnSignup = findViewById(R.id.btnSignUp)

        btnSignup.setOnClickListener {
            val intent = Intent(this,SignUp::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPass.text.toString()

            login(email,password);
        }

    }

    private fun login(email: String, password: String){
        //logic for logging in user
        if (email.isNotEmpty() && password.isNotEmpty()) {

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    //if authentication is successfull then move it to Home screen
                    val intent = Intent(this@LogIn, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Empty Fields Are Not Allowed !!", Toast.LENGTH_SHORT).show()
        }
    }

    //if user is already signin then after restarting the app user does not need to sign in
    override fun onStart() {
        super.onStart()

        if (mAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}