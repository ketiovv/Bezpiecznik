package com.example.bezpiecznik.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.bezpiecznik.R
import com.example.bezpiecznik.R.*
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonGoToAbout.setOnClickListener {
            it.findNavController().navigate(R.id.action_settingsFragment_to_aboutFragment)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}