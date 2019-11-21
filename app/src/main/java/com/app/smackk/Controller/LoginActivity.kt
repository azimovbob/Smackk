package com.app.smackk.Controller

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.app.smackk.R
import com.app.smackk.Services.AuthService
import kotlinx.android.synthetic.main.activity_creat_user.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginSpinner.visibility = View.INVISIBLE
    }

    fun loginBtnClicked(view: View) {
        enableSpinner(true)
        val email = loginEmailTxt.text.toString()
        val password = loginPassTxt.text.toString()
        hideKeyboard()

        if(email.isNotEmpty() && password.isNotEmpty()){

        AuthService.loginUser(this, email, password) { loginSuccess ->
            if (loginSuccess) {
                AuthService.findUserByEmail(this) { findSucces ->
                    if (findSucces) {
                        enableSpinner(false)
                        finish()
                    } else Toast.makeText(this, "cant find user", Toast.LENGTH_SHORT).show()
                }
            } else Toast.makeText(
                this,
                "something went wrong login activity",
                Toast.LENGTH_SHORT
            ).show()
        }

       }
        else{
            Toast.makeText(this, "Please fill the information", Toast.LENGTH_SHORT).show()
            enableSpinner(false)
        }

    }

    fun loginCreateBtnClicked(view: View) {

        val createUserIntent= Intent(this, CreateUserActivity::class.java)
        startActivity(createUserIntent)
        finish()


    }

    fun errorToast(){
        Toast.makeText(this, "Something went wrong please try again", Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }

    fun enableSpinner(enable: Boolean){
        if(enable){
            loginSpinner.visibility =View.VISIBLE

        }

        else{
            loginSpinner.visibility = View.INVISIBLE
        }

        loginBtn.isEnabled = !enable
        loginCreateBtn.isEnabled = !enable


    }

    fun hideKeyboard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if(inputManager.isAcceptingText){
            inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }
}



