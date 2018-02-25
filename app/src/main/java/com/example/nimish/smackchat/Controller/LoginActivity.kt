package com.example.nimish.smackchat.Controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.nimish.smackchat.R

class LoginActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

   fun loginLoginButtonClicked(view: View)
   {

   }

   fun loginSignupButtonClicked(view:View)
   {
       var createUserIntent=Intent(this@LoginActivity, CreateUserActivity::class.java)
       startActivity(createUserIntent)
   }
}
