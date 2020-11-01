package com.example.apiconnection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getInfoFromApi()
    }

    private val client = OkHttpClient()

    fun getInfoFromApi() {
        val request = Request.Builder()
            .url("https://api.jsonbin.io/b/5f9f3dd7ce4aa2289554456b")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    for ((name, value) in response.headers) {
                        Log.d("test","$name: $value")
                    }

                    Log.d("test",response.body!!.string())
                }
            }
        })
    }
}