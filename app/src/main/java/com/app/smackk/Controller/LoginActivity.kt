package com.app.smackk.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.app.smackk.R
import com.app.smackk.Services.AuthService
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun loginBtnClicked(view: View) {

        val email = loginEmailTxt.text.toString()
        val password = loginPassTxt.text.toString()


        AuthService.loginUser(this, email, password) {loginSuccess->
            if(loginSuccess) {
                AuthService.findUserByEmail(this){findSucces->
                    if(findSucces){
                        finish()
                    }else Toast.makeText(this, "cant find user", Toast.LENGTH_SHORT).show()
                }
            }else Toast.makeText(this, "something went wrong login activity", Toast.LENGTH_SHORT).show()
        }


    }

    fun loginCreateBtnClicked(view: View) {

        val createUserIntent= Intent(this, CreateUserActivity::class.java)
        startActivity(createUserIntent)
        finish()


    }

}

