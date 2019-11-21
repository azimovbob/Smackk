package com.app.smackk.Services

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.app.smackk.Model.Channel
import com.app.smackk.Utilities.URL_CREATE_USER
import com.app.smackk.Utilities.URL_GET_CHANNELS
import org.json.JSONException

object MessageService {

    val channels = ArrayList<Channel>()

    fun getChanels(context: Context, complete: (Boolean)->Unit){


        val channelRequest = object : JsonArrayRequest(Method.GET, URL_GET_CHANNELS, null, Response.Listener {response ->


            try{

                for(x in 0 until response.length()){
                    val channel = response.getJSONObject(x)
                    val name = channel.getString("name")
                    val description = channel.getString("description")
                    val channelId = channel.getString("_id")

                    val newChannel = Channel(name, description, channelId)

                    this.channels.add(newChannel)
                }

                complete(true)

            }catch(e: JSONException){
                Log.d("JSON", "EXC: " + e.localizedMessage)
                complete(false)
            }

        }, Response.ErrorListener {error ->
            Log.d("Error", "Could not retrieve channels ")
            complete(false)

        }){

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {

                val header = HashMap<String, String>()
                header.put("Authorization", "Bearer ${AuthService.authToken}")
                return header
            }
        }
        Volley.newRequestQueue(context).add(channelRequest)
    }



}