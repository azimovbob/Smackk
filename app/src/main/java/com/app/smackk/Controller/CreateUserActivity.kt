package com.app.smackk.Controller

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.smackk.R
import com.app.smackk.Services.AuthService
import com.app.smackk.Services.UserDataService
import kotlinx.android.synthetic.main.activity_creat_user.*
import kotlin.random.Random

class CreateUserActivity : AppCompatActivity() {

    private var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creat_user)
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
        val username = createUserNameTxt.text.toString()

        val email = createEmailTxt.text.toString()
        val password = createPasswordTxt.text.toString()

        AuthService.registerUser(this, email,password){registerSuccess->
            if(registerSuccess){
                AuthService.loginUser(this,email,password){loginSuccess->
                    if(loginSuccess){
                        AuthService.createUser(this,username, email, userAvatar, avatarColor){createSuccess->
                            if(createSuccess){
                                println(UserDataService.avatarName)
                                println(UserDataService.avatarColor)
                                println(UserDataService.name)
                                finish()
                            }

                        }
                    }
                }
            }

        }
    }
}
