package com.rishabhkumar.weatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

class splash_screen : AppCompatActivity() {
    lateinit var mfusedlocation : FusedLocationProviderClient
    private var myRequestCode = 1010

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        mfusedlocation = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()



        //splashing the screen for seconds
//        Handler(Looper.getMainLooper()).postDelayed({
//            val intent = Intent(this,MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        },2000)

    }



    //scenarios which app can face
    //1. location permission deny
    //2. location permission allowed first time but denied through setting
    //3. gps off kr diya
    //4. permission le lo

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if(CheckPermission()) { //check krega ki permission mili h ki nhi?
            if(LocationEnable()){   //check krega ki gps on h ki nhi?
                mfusedlocation.lastLocation.addOnCompleteListener{
                    task->
                    var location:Location?= task.result
                    if(location == null){   //agr location null hui toh fucntion ke through pta krega
                        NewLocation()
                    }else{  //location null nhi h usko add kr lega
//                        Log.i("Location",location.longitude.toString())
//                        Toast.makeText(this,location.latitude.toString(),Toast.LENGTH_LONG).show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            val intent = Intent(this,MainActivity::class.java)
                            intent.putExtra("lat",location.latitude.toString())
                            intent.putExtra("long",location.longitude.toString())
                            startActivity(intent)
                            finish()
                        },2000)
                    }
                }
            }else{  //gps off hua toh message dega ki on kro
                Toast.makeText(this,"Please turn on your GPS Location",Toast.LENGTH_LONG).show()
            }
        }else{      //permission nhi h toh ask krega ki permission do location find krne ki
            RequestPermission()
        }
    }

    @SuppressLint("MissingPermission")
    private fun NewLocation() { //function for finding the gps location
        var locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        mfusedlocation = LocationServices.getFusedLocationProviderClient(this)
        mfusedlocation.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper()!!)
    }

    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            var lastLocation:Location = p0.lastLocation
        }
    }

    //function for enable the gps
    private fun LocationEnable(): Boolean {
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    //function for requesting the permission to access gps location
    private fun RequestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION),myRequestCode)
    }

    //function for checking the whether the permission is given or not
    private fun CheckPermission(): Boolean {
        if(
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ){
            return true
        }
        return false
    }

    //override function to grant the location
    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == myRequestCode){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation()
            }
        }
    }
}