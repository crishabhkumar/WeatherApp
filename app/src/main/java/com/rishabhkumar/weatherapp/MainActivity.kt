package com.rishabhkumar.weatherapp

import android.graphics.Color
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
import org.json.JSONObject
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lat = intent.getStringExtra("lat")
        val long = intent.getStringExtra("long")
        //to check we are getting latt,long or not?
        //Toast.makeText(this,lat + "and" + long,Toast.LENGTH_LONG).show()

        //another way to change status bar color
        //window.statusBarColor = Color.parseColor("#1383C3")
        getJsonData(lat,long)
    }


    //40mb -> 100kb -> 4 min


    private fun getJsonData(lat: String?, long: String?) {
        val apiKey = "your_api_key"
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${apiKey}"

        // Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET,url,null,
            Response.Listener{ response ->
                //to check response is correct or not
                //hellotext.text = response.toString()
                // Toast.makeText(this,response.toString(),Toast.LENGTH_LONG).show()
                setValues(response)

            },
            Response.ErrorListener {
                Toast.makeText(this,"Error in API CALL",Toast.LENGTH_LONG).show()
            })

        // Add the request to the RequestQueue.
        queue.add(jsonRequest)
    }

    private fun setValues(response: JSONObject?) {
        //setting up the city name
        txtCityName.text = response?.getString("name")

        //setting up the longitude and lattitude
        var lat = response?.getJSONObject("coord")?.getString("lat")
        var long = response?.getJSONObject("coord")?.getString("lon")
        txtCordinates.text = "${lat} ,${long}"

        //setting up the weather
        txtWeather.text = response?.getJSONArray("weather")?.getJSONObject(0)?.getString("main")

        //setting up the temperature
        var tempr = response?.getJSONObject("main")?.getString("temp")
        tempr = ((((tempr)!!.toFloat() - 273.15)).toInt()).toString()
        txtTemperature.text = "${tempr} °C"

        //setting up the maximum and minimum temperature
        var minTempr = response?.getJSONObject("main")?.getString("temp_min")
        minTempr = ((((minTempr)!!.toFloat() - 273.15)).toInt()).toString()
        var maxTempr = response?.getJSONObject("main")?.getString("temp_max")
        maxTempr = ((ceil((maxTempr)!!.toFloat() - 273.15)).toInt()).toString()
        txtTempMinMax.text = "Min:${minTempr}, Max:${maxTempr}"

        //setting up the pressure value
        txtPressure.text = response?.getJSONObject("main")?.getString("pressure")

        //setting up the humidity value
        txtHumidity.text = response?.getJSONObject("main")?.getString("humidity") + "%"

        //setting up the wind speed value
        txtWindSpeed.text = response?.getJSONObject("wind")?.getString("speed")

        //setting up the degree
        val deg = response?.getJSONObject("wind")?.getString("deg")
        txtDegree.text = "Degree: ${deg}"

        //setting up the gust value
//        txtGust.text = "Gust: " + response?.getJSONObject("wind")?.getString("gust")


    }
}
