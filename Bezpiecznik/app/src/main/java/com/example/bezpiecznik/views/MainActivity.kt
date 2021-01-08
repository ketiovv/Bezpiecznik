package com.example.bezpiecznik.views

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bezpiecznik.R
import com.example.bezpiecznik.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPref : SharedPreferences
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.fragment)
        bottomNavigationView.setupWithNavController(navController)

        sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        if(!sharedPref.contains(getString(R.string.userID))){
            bottomNavigationView.visibility = View.GONE
            navController.navigate(R.id.createUserFragment)
        }

    }
}