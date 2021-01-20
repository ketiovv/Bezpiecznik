package com.example.bezpiecznik.viewmodels


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezpiecznik.models.api.ApiRoutes
import com.example.bezpiecznik.models.api.IApiRequest
import com.example.bezpiecznik.models.api.Records
import com.example.bezpiecznik.models.entities.Session
import com.example.bezpiecznik.models.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@RequiresApi(Build.VERSION_CODES.O)
class StatsViewModel : ViewModel() {
    var sessionList =  MutableLiveData<ArrayList<Session>>()

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build();

    private val api = Retrofit.Builder().baseUrl(ApiRoutes.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
        .create(IApiRequest::class.java)



    init {
        //addMockDataToSessions()
        getSessions(){}
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addMockDataToSessions(){
        sessionList.postValue(arrayListOf(Session(LocalDateTime.now().toString(),null,""), Session(LocalDateTime.now().toString(),null,"")))
    }

    fun addSession(session: Session){
        Log.d("myTag","Hello form add Session")
        sessionList.value!!.add(session)
        GlobalScope.launch(Dispatchers.IO) {
            val response = api.addSession( UserViewModel.collectionID, Records(sessionList.value!!)).awaitResponse()
            if (response.isSuccessful){
                val data = response.body()
                if(data != null){
                    //UserViewModel.collectionID = data.name
                    Log.d("myTagSTVM", "chuj")
                    //sessionList.value!!.add(session)
                }
            }
            else{

                Log.d("api-connection",response.message())
            }
        }
    }

    fun getSessions( doneCallback: ((d: Boolean) -> Unit) ){
        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getUserCollection(UserViewModel.collectionID).awaitResponse()
            if (response.isSuccessful){
                val data = response.body()
                if(data != null){
                    Log.d("statsViewModel", data.records[data.records.size -1].attempt.toString())
                    sessionList.postValue(data.records)
                    doneCallback(true)
                }
            }
            else{
                Log.d("api-connection","response failed")
            }

    }

    fun getSession(id: String){

    }
    //        var userList = MutableLiveData<MutableList<User>>()
//
//
//        var user = User("1","Konrad","Konrad123", mutableListOf(),
//            Pattern(mutableListOf(Cell(1,0))),Settings())
//
//        var gson = Gson()
//        var jsonString = gson.toJson(user)
//        Log.d("myTag",jsonString)
//
//        val api = Retrofit.Builder().baseUrl(ApiRoutes.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(IApiRequest::class.java)
//
//        //api.addJSon(user)
//
//
//        GlobalScope.launch(Dispatchers.IO) {
//            val response = api.getJson().awaitResponse()
//            if (response.isSuccessful){
//                val data= response.body()!!
//                // postValue instead of value, because it's called asynchronous
//                userList.postValue(mutableListOf(data))
//                Log.d("api-connection",userList.value!!.toString())
//
//            }
//            else{
//                Log.d("api-connection","response failed")
//                userList.postValue(ArrayList())
//            }
//        }
}}