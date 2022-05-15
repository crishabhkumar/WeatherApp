package com.rishabhkumar.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import kotlin.math.ceil

class ChoiceCityName : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_choice_of_city_name)
        val cityName = intent.getStringExtra("cityName")
        getJsonData(cityName)
    }

    private fun getJsonData(cityName: String?) {

        Toast.makeText(this,cityName,Toast.LENGTH_SHORT).show()
        val apiKey = "d8e59f309ce94473b0e93012221505"
        val queue = Volley.newRequestQueue(this)
        val url = "http://api.weatherapi.com/v1/current.json?key=${apiKey}&q=${cityName}&aqi=no"


        val jsonRequest = JsonObjectRequest(
            Request.Method.GET,url,null,
            Response.Listener{ response ->
                //to check response is correct or not
                //hellotext.text = response.toString()
                // Toast.makeText(this,response.toString(),Toast.LENGTH_LONG).show()
                Toast.makeText(this,response.toString(),Toast.LENGTH_LONG).show()

            },
            Response.ErrorListener {
                Toast.makeText(this,"Error in API CALL",Toast.LENGTH_LONG).show()
            })

        // Add the request to the RequestQueue.
        queue.add(jsonRequest)
//        val jsonObjectRequest = object : JsonObjectRequest(
//            Request.Method.GET, url,null,
//            Response.Listener{
//                             Toast.makeText(this,it.toString(),Toast.LENGTH_LONG).show()
//        },Response.ErrorListener {
//            Toast.makeText(this,"Api error",Toast.LENGTH_LONG).show()
//        })
//
//        queue.add(jsonObjectRequest)

    }

    private fun setValues(response: JSONObject?) {

    }
}