package com.example.nimish.smackchat.Controller

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.nimish.smackchat.R
import com.example.nimish.smackchat.Services.AuthService
import com.example.nimish.smackchat.Services.UserDataService
import com.example.nimish.smackchat.Utilities.BROADCAST_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity()
{
    var userAvatar="profileDefault"
    var avatarColor="[0.5,0.5,0.5,1]"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        createSpinner.visibility=View.INVISIBLE
    }

    fun createUserClicked(view: View)
    {   enableSpinner(true)
        val username=createUsernameText.text.toString()
        val email=createEmailText.text.toString()
        val password=createPasswordText.text.toString()
        if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty())
        {
            AuthService.registerUser(this, email, password)
            { registerSucess ->
                if (registerSucess)
                {
                    AuthService.loginUser(this, email, password)
                    { loginSucess ->
                        if (loginSucess)
                        {
                            AuthService.createUser(this, email, username, userAvatar, avatarColor)
                            { createSuccess ->
                                Log.e("HEHEHEHE", "createSuccess is ${createSuccess}")
                                if (createSuccess)
                                {
                                    //WILL NOW USE LOCAL BROADCAST MANAGER
                                    val userDataChange= Intent(BROADCAST_USER_DATA_CHANGE)
                                    LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)
                                    enableSpinner(false)
                                    finish()
                                }
                                else
                                {
                                    errorToast()
                                }
                            }
                        }
                        else
                        {
                            errorToast()
                        }
                    }
                    Log.i("USERCLICKED", "___________________")
                    Log.i("USERCLICKED", "registerUser")
                    Log.i("USERCLICKED", "___________________")
                }
                else
                {
                    errorToast()
                }

            }

        }
        else
        {
            Toast.makeText(this@CreateUserActivity,"make sure user and email and password are filled in ",Toast.LENGTH_SHORT).show()
            enableSpinner(false)
        }

    }
    fun generateColorClicked(view: View)
    {
        var random=Random()
        var r = random.nextInt(255)
        var g =random.nextInt(255)
        var b=random.nextInt(255)
        createAvatarImageView.setBackgroundColor(Color.rgb(r,g,b))

        var savedr=r.toDouble() / 255
        var savedg=g.toDouble() / 255
        var savedb=b.toDouble() / 255

        avatarColor="[$savedr,$savedg,$savedb,1]"
    }
    fun generateUserAvatar(view: View)
    {
        var random = Random()
        var color=random.nextInt(2)
        var avatar=random.nextInt(28)
        if (color==0)
        {
            userAvatar="light$avatar"
        }
        else
        {
            userAvatar="dark$avatar"
        }
        var resourceId=resources.getIdentifier(userAvatar,"drawable",packageName)
        createAvatarImageView.setImageResource(resourceId)


    }

    fun enableSpinner(enable:Boolean)
    {
        if (enable)
        {
            createSpinner.visibility=View.VISIBLE
        }
        else
        {
            createSpinner.visibility = View.INVISIBLE
        }
            createUserBtn.isEnabled=!enable
            createAvatarImageView.isEnabled=!enable
            createBackgroundColorBtn.isEnabled=!enable

    }

    fun errorToast()
    {
        enableSpinner(false)
        Toast.makeText(this@CreateUserActivity,"SOMETHING WENT WRONG",Toast.LENGTH_SHORT).show()
    }

}
