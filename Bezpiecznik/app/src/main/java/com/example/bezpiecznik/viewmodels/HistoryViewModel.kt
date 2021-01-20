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


    private val api = Retrofit.Builder().baseUrl(ApiRoutes.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()
        .create(IApiRequest::class.java)


    @RequiresApi(Build.VERSION_CODES.O)
    private fun addMockDataToSessions(){
        sessionList.postValue(arrayListOf(Session(LocalDateTime.now().toString(),null,""), Session(LocalDateTime.now().toString(),null,"")))
    }

    fun addSession(session: Session,  doneCallback: ((d: Boolean) -> Unit)){
        Log.d("myTag","Hello form add Session")
        sessionList.value!!.add(session)
        GlobalScope.launch(Dispatchers.IO) {
            val response = api.addSession( UserViewModel.collectionID, Records(sessionList.value!!)).awaitResponse()
            if (response.isSuccessful){
                val data = response.body()
                if(data != null){
                    Log.d("myTagSTVM", "chuj")
                    doneCallback(true)

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
                    if (data.records[0].userId == "")
                        data.records.removeAt(0)
                    //data.records.reverse()
                    sessionList.postValue(data.records)
                    doneCallback(true)
                }
            }
            else{
                Log.d("api-connection","response failed")
            }

    } }

    companion object{
        var dataReady = MutableLiveData<Boolean>()
    }
}