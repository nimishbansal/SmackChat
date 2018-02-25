package com.example.nimish.smackchat.Services

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.nimish.smackchat.Utilities.URL_CREATE_USER
import com.example.nimish.smackchat.Utilities.URL_LOGIN
import com.example.nimish.smackchat.Utilities.URL_REGISTER
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by nimish on 20/2/18.
 */
object AuthService
{
    var isLoggedIn=false
    var userEmail=""
    var authToken=""


    /*
    register user will create one user table having user email and password
    */
    fun registerUser(context: Context, email: String, password: String, complete: (Boolean) -> Unit)
    {

        val jsonBody = JSONObject()
        jsonBody.put("email", email)
        jsonBody.put("password", password)
        val requestBody = jsonBody.toString()

        val registerRequest = object : StringRequest(Method.POST, URL_REGISTER, Response.Listener { response ->
            println(response)
            complete(true)
        },
                Response.ErrorListener { error ->
            Log.i("MYTAG", "Could not register user: $error")
            complete(false)
        })
        {
            override fun getBodyContentType(): String
            {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray
            {
                return requestBody.toByteArray()
            }
        }
        Log.i("MYTAG",registerRequest.url)
        Volley.newRequestQueue(context).add(registerRequest)
    }

    fun loginUser(context: Context,email:String,password: String,complete: (Boolean) -> Unit)
    {
        val jsonBody = JSONObject()
        jsonBody.put("email", email)
        jsonBody.put("password", password)
        val requestBody = jsonBody.toString()

        val loginRequest=object:JsonObjectRequest(Method.POST, URL_LOGIN,null,Response.Listener {response->
            //this is where we parse json object
            println(response)

            try
            {
                authToken=response.getString("token")
                userEmail=response.getString("user")
                isLoggedIn=true
                complete(true)
            }
            catch(ex:JSONException)
            {
                Log.i("MYTAG","EXC "+ex.localizedMessage)
                complete(false)
            }



        },Response.ErrorListener {
            //this is where he deal with error
            error ->

            Log.i("MYTAG", "Could not login user: $error")

            complete(false)
        })
        {
            override fun getBodyContentType(): String
            {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray
            {
                return requestBody.toByteArray()
            }
        }
       Volley.newRequestQueue(context).add(loginRequest)
    }

    /*
    create user will create one user table having user details
    */
    fun createUser(context: Context,email:String,name:String,avatarName:String,avatarColor:String,complete:(Boolean)->Unit)
    {
        val jsonBody = JSONObject()
        jsonBody.put("name", name)
        jsonBody.put("email", email)
        jsonBody.put("avatarName",avatarName)
        jsonBody.put("avatarColor",avatarColor)
        val requestBody=jsonBody.toString()

        val createRequest=object:JsonObjectRequest(Method.POST, URL_CREATE_USER, null,
                Response.Listener{
                    response->
                    try
                    {
//                        var key1=response.keys()
//                        for (i in 1..5)
//                        {
//                            Log.d("JSON","EXCC"+key1.next().toString())
//                        }
                        UserDataService.avatarColor=response.getString("avatarColor")
                        UserDataService.id=response.getString("_id")
                        UserDataService.email=response.getString("email")
                        UserDataService.avatarName=response.getString("avatarName")
                        UserDataService.name=response.getString("name")
                        complete(true)
                    }
                    catch(ex:JSONException)
                    {
                        Log.d("MYTAG","EXCC"+ex.localizedMessage)
                        complete(false)
                    }

                }, Response.ErrorListener {error->
                    Log.d("MYTAG","COULD NOT ADD USER")
                    complete(false)

                }
        )
        {
            override fun getBodyContentType(): String
            {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray
            {
                return requestBody.toByteArray()
            }

            override fun getHeaders(): MutableMap<String, String>
            {
                val headers=HashMap<String,String>()
                headers.put("Authorization","Bearer $authToken")
                return headers
            }
        }
        Volley.newRequestQueue(context).add(createRequest)
    }

}
