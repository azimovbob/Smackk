package com.app.smackk.Controller

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.app.smackk.R
import com.app.smackk.Services.AuthService
import com.app.smackk.Services.UserDataService
import com.app.smackk.Utilities.BROADCAST_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_creat_user.*
import kotlin.random.Random

class CreateUserActivity : AppCompatActivity() {

    private var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creat_user)
        createSpinner.visibility = View.INVISIBLE
    }

    fun generateUserAvatar(view: View) {

        val random = Random
        val color = random.nextInt(2)
        val avatar = random.nextInt(28)



        userAvatar = if(color==0){
            "light$avatar"
        } else{
            "dark$avatar"
        }

        val resourceId = resources.getIdentifier(userAvatar, "drawable", packageName)
        createAvatarImage.setImageResource(resourceId)

    }



    fun generateColorClicked(view: View) {

        val random = Random
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)

        createAvatarImage.setBackgroundColor(Color.rgb(r,g,b))


        val savedR = r.toDouble() / 255
        val savedG = r.toDouble() / 255
        val savedB = r.toDouble() / 255

        avatarColor = "[$savedR, $savedG, $savedB, 1]"

        println(avatarColor)

    }
    fun createUserBtnClicked(view: View) {

        enableSpinner(true)
        val username = createUserNameTxt.text.toString()

        val email = createEmailTxt.text.toString()
        val password = createPasswordTxt.text.toString()


        if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {

            AuthService.registerUser(this, email, password) { registerSuccess ->
                if (registerSuccess) {
                    AuthService.loginUser(this, email, password) { loginSuccess ->
                        if (loginSuccess) {
                            AuthService.createUser(this, username, email, userAvatar, avatarColor
                            ) { createSuccess ->
                                if (createSuccess) {
                                    val userDataChanged = Intent(BROADCAST_USER_DATA_CHANGE)
                                    LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChanged)
                                    enableSpinner(false)
                                    finish()
                                } else {
                                    errorToast()
                                }

                            }
                        } else {
                            errorToast()
                        }
                    }
                } else {
                    errorToast()
                }

            }
        }
        else {
            Toast.makeText(this, "Make sure all field are not empty", Toast.LENGTH_LONG).show()
        }

    }

    fun errorToast(){
        Toast.makeText(this, "Something went wrong please try again", Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }

    fun enableSpinner(enable: Boolean){
        if(enable){
            createSpinner.visibility =View.VISIBLE

        }

        else{
            createSpinner.visibility = View.INVISIBLE
        }

        createUserBtn.isEnabled = !enable
        createAvatarImage.isEnabled = !enable
        createBkgrdColorBtn.isEnabled = !enable

    }
}
