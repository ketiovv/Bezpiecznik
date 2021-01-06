package com.example.bezpiecznik.viewmodels


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezpiecznik.models.entities.Session
import com.example.bezpiecznik.models.entities.User
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class StatsViewModel : ViewModel() {
    var sessionList =  MutableLiveData<MutableList<Session>>()

    init {
        addMockDataToSessions()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addMockDataToSessions(){
        sessionList.postValue(mutableListOf(Session(LocalDateTime.now(), LocalDateTime.now(),null,null), Session(LocalDateTime.now(), LocalDateTime.now(),null,null)))
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
}