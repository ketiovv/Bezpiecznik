package com.example.bezpiecznik.views

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.BoringLayout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.bezpiecznik.R
import com.example.bezpiecznik.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login_user.*

class LoginUserFragment : Fragment() {

    private var userLoaded: Boolean = false
    private lateinit var userViewModel: UserViewModel
    private lateinit var sharedPref : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)

        userViewModel.getUser(sharedPref.getString(getString(R.string.binID), "")!!){
            if(it){
                userLoaded = true
                UserViewModel.binID = sharedPref.getString(getString(R.string.binID), "")!!
                Log.d("myTag",UserViewModel.user.toString())
            }

        }

        return inflater.inflate(R.layout.fragment_login_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonLoginUser.setOnClickListener{
            var masterPassword = editTextLoginPassword.text.toString()
            if(userLoaded &&  masterPassword != ""){
                if(masterPassword == UserViewModel.user.masterPassword){
                    activity?.bottomNavigationView!!.visibility = View.VISIBLE
                    it.findNavController().navigate(R.id.testsFragment)
                }
                else{
                    editTextLoginPassword.error = "Wrong Password"
                }
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginUserFragment()
    }
}