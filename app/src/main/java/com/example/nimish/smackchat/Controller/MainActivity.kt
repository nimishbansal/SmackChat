package com.example.nimish.smackchat.Controller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.nimish.smackchat.R
import com.example.nimish.smackchat.Services.AuthService
import com.example.nimish.smackchat.Services.UserDataService
import com.example.nimish.smackchat.Utilities.BROADCAST_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*

class MainActivity : AppCompatActivity()
{
    private val userDataChangeReciever=object:BroadcastReceiver()
    {
        override fun onReceive(context: Context?, intent: Intent?)
        {
            if(AuthService.isLoggedIn)
            {
                usernameNavHeader.text=UserDataService.name
                userEmailNavHeader.text=UserDataService.email
                val resourceId=resources.getIdentifier(UserDataService.avatarName,"drawable",packageName)
                userImageNavHeader.setImageResource(resourceId)
                loginNavHeader.text="Logout"
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        LocalBroadcastManager.getInstance(this).registerReceiver(userDataChangeReciever, IntentFilter(BROADCAST_USER_DATA_CHANGE))
    }

    override fun onBackPressed()
    {
        if (drawer_layout.isDrawerOpen(GravityCompat.START))
        {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        else
        {
            super.onBackPressed()
        }
    }
    fun loginButtonNavClicked(view:View)
    {
        Toast.makeText(this@MainActivity,"login btn clicked",Toast.LENGTH_LONG).show()
        var loginIntent=Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(loginIntent)
    }
    fun addChannelNavClicked(view:View)
    {
        Toast.makeText(this@MainActivity,"add channel clicked",Toast.LENGTH_LONG).show()

    }
    fun sendMessageButtonClicked(view:View)
    {
        Toast.makeText(this@MainActivity,"send msg button clicked",Toast.LENGTH_LONG).show()

    }


}
