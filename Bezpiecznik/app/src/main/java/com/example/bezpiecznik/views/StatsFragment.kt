package com.example.bezpiecznik.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bezpiecznik.R
import com.example.bezpiecznik.models.api.ApiRoutes
import com.example.bezpiecznik.models.api.IApiRequest
import com.example.bezpiecznik.models.entities.*
import com.example.bezpiecznik.adapters.StatsListAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_stats.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class StatsFragment : Fragment() {
//    lateinit var viewManager: RecyclerView.LayoutManager
//    lateinit var statsListAdapter: StatsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//      TODO: Update the json get logic

//        viewManager = LinearLayoutManager(requireContext())
//
//        // Inflate the layout for this fragment
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

       // statsListAdapter = StatsListAdapter()


        return inflater.inflate(R.layout.fragment_stats, container, false)


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        recyclerView.apply{
//            adapter=statsListAdapter
//            layoutManager=viewManager
//        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = StatsFragment()
    }
}