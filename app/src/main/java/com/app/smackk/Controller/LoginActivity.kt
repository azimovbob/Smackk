package com.app.smackk.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.View
import com.app.smackk.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun loginBtnClicked(view: View) {


    }

    fun loginCreateBtnClicked(view: View) {

        val createUserIntent= Intent(this, CreateUserActivity::class.java)
        startActivity(createUserIntent)


    }

}
