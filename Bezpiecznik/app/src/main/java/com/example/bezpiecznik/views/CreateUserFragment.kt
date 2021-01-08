package com.example.bezpiecznik.views

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.bezpiecznik.R
import com.example.bezpiecznik.models.entities.User
import com.example.bezpiecznik.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_create_user.*
import kotlinx.android.synthetic.main.fragment_settings.*

class CreateUserFragment : Fragment() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var sharedPref : SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)

        return inflater.inflate(R.layout.fragment_create_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        buttonCreateUser.setOnClickListener{

            var userName: String = editTextUserName.getText().toString()
            var userPassword: String = editTextTextPassword.getText().toString()
           if( userName != "" && userPassword != ""){
                userViewModel.createUser(userName , userPassword){ user: User, s: String ->
                    UserViewModel.user = user
                    UserViewModel.binID = s
                    sharedPref.edit().putString(getString(R.string.binID),s).commit()
                    sharedPref.edit().putString(getString(R.string.userID),user.id).commit()
                    activity?.runOnUiThread {
                        activity?.bottomNavigationView!!.visibility = View.VISIBLE
                        it.findNavController().navigate(R.id.testsFragment)
                    }
                }
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = CreateUserFragment()
    }
}