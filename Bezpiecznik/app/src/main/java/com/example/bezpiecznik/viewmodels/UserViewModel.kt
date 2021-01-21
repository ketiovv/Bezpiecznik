package com.example.bezpiecznik.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.bezpiecznik.models.api.ApiRoutes
import com.example.bezpiecznik.models.api.IApiRequest
import com.example.bezpiecznik.models.api.Records
import com.example.bezpiecznik.models.api.Response
import com.example.bezpiecznik.models.entities.Attempt
import com.example.bezpiecznik.models.entities.Session
import com.example.bezpiecznik.models.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom

class UserViewModel() : ViewModel() {

    private val api = Retrofit.Builder().baseUrl(ApiRoutes.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()
        .create(IApiRequest::class.java)

    fun createUser(name: String, masterPassword : String, saveToSPCallback:((u: User, id: String) -> Unit)){
        val userID: String = generateUserId()
        val user = User(userID, name, masterPassword)
        GlobalScope.launch(Dispatchers.IO) {
            val response  = api.addUser(user).awaitResponse()
            if (response.isSuccessful){
                val data = response.body()
                if (data != null) {
                        saveToSPCallback(user,data.name)
                }
            }
            else{
                Log.d("api-connection","response failed")
            }
        }
    }

    fun getUser(binID: String, doneCallback: ((d: Boolean) -> Unit)){

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getUser(binID).awaitResponse()
            if (response.isSuccessful){
                val data = response.body()
                if(data != null){
                    user = data
                    doneCallback(true)
                }
            }
            else{
                Log.d("api-connection","response failed")
            }
        }
    }

    fun createUserCollection(doneCallback: ((d: Boolean) -> Unit)){
        GlobalScope.launch(Dispatchers.IO) {
            val response = api.createUserCollection(
                    Records( arrayListOf(  Session("",
                mutableListOf(Attempt("","",0,0)),""))
                )).awaitResponse()
            if (response.isSuccessful){
                val data = response.body()
                if(data != null){
                    collectionID = data.name
                    doneCallback(true)
                }
            }
            else{
                Log.d("api-connectionUVM","response failed")
            }
        }
    }

    private fun generateUserId(): String {
        val random = SecureRandom()
        var randomString: String = ""
        random.setSeed(random.generateSeed(20))
        for (i in 1..10){
            randomString += (random.nextInt(1000 - 1 + 1) + 1000).toString()
        }
        return  randomString
    }


    companion object{
        lateinit var user: User
        lateinit var binID : String
        lateinit var collectionID: String
    }
}