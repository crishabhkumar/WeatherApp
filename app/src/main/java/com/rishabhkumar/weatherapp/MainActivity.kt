package com.rishabhkumar.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lat = intent.getStringExtra("lat")
        val long = intent.getStringExtra("long")
        //to check we are getting latt,long or not?
        //Toast.makeText(this,lat + "and" + long,Toast.LENGTH_LONG).show()

        getJsonData(lat,long)
    }


    //40mb -> 100kb -> 4 min


    private fun getJsonData(lat: String?, long: String?) {
        val apiKey = "b20db2826d78cfdc7d32cdf0c5787a68"
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${apiKey}"

        // Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET,url,null,
            Response.Listener{ response ->
                //to check response is correct or not
                //hellotext.text = response.toString()
                // Toast.makeText(this,response.toString(),Toast.LENGTH_LONG).show()


            },
            Response.ErrorListener {
                Toast.makeText(this,"Error in API CALL",Toast.LENGTH_LONG).show()
            })

        // Add the request to the RequestQueue.
        queue.add(jsonRequest)
    }
}