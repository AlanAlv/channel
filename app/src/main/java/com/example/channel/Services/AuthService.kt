package com.example.channel.Services

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.channel.Utilities.URL_CREATE_USER
import com.example.channel.Utilities.URL_LOGIN
import com.example.channel.Utilities.URL_REGISTER
import com.example.channel.Utilities.UserDataService
import org.json.JSONException
import org.json.JSONObject

object AuthService {
    var isLoggedIn = false
    var userEmail = ""
    var authToken = ""

    fun registerUser(context: Context, email: String, password: String, complete: (Boolean) -> Unit){
        var url = URL_REGISTER

        //JSON to pass email and body to request
        val jsonBody = JSONObject()
        jsonBody.put("email", email)
        jsonBody.put("password", password)

        //Convert to string to convert to byte array
        val requestBody = jsonBody.toString()

        val registerRequest = object : StringRequest(Request.Method.POST, url, Response.Listener{response ->
            println(response)
            complete(true)
        }, Response.ErrorListener {error ->
            Log.d("ERROR", "Could not register user: $error")
            complete(false)
        }){
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }
        Volley.newRequestQueue(context).add(registerRequest)
    }

    fun logibUser(context: Context, email: String, password: String, complete: (Boolean) -> Unit){
        //JSON to pass email and body to request
        val jsonBody = JSONObject()
        jsonBody.put("email", email)
        jsonBody.put("password", password)

        //Convert to string to convert to byte array
        val requestBody = jsonBody.toString()


        val loginRequest = object: JsonObjectRequest(Method.POST, URL_LOGIN, null, Response.Listener {response ->
            try {
                //Get information from the json object
                userEmail = response.getString("user")
                authToken = response.getString("token")
                isLoggedIn = true
                complete(true)
            } catch(e: JSONException) {
                Log.d("JSON", "EXC: " + e.localizedMessage)
                complete(false)
            }



        }, Response.ErrorListener {error ->
            Log.d("ERROR", "Could not register user: $error")
            complete(false)
        }){
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }
        Volley.newRequestQueue(context).add(loginRequest)

    }

    fun createUser(context: Context, name: String, email: String, avatarName: String, avatarColor: String, complete: (Boolean) -> Unit) {
        //JSON to pass email and body to request
        val jsonBody = JSONObject()
        jsonBody.put("name", name)
        jsonBody.put("email", email)
        jsonBody.put("avatarName", avatarName)
        jsonBody.put("avatarColor", avatarColor)

        //Convert to string to convert to byte array
        val requestBody = jsonBody.toString()

        val createRequest = object : JsonObjectRequest(Method.POST, URL_CREATE_USER, null, Response.Listener { response ->

            try {
                UserDataService.name = response.getString("name")
                UserDataService.email = response.getString("email")
                UserDataService.avatarName = response.getString("avatarName")
                UserDataService.avatarColor = response.getString("avatarColor")
                UserDataService.id = response.getString("_id")
                complete(true)

            } catch (e : JSONException){
                Log.d("JSON", "EXC: " + e.localizedMessage)
                complete(false)
            }

            }, Response.ErrorListener {error ->
                Log.d("ERROR", "Could not add user: $error")
                complete(false)
            }) {

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer $authToken")
                return headers
            }
        }
        Volley.newRequestQueue(context).add(createRequest)
    }
}