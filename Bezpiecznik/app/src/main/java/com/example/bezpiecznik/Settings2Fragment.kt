package com.example.bezpiecznik

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SeekBarPreference

class Settings2Fragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        when(preference?.key){
            "reset_btn" -> {
                // TODO: Make definitely enabled because when we change fragment, it's disable again. It should be enabled till user set new pattern!
                preferenceScreen.findPreference<SeekBarPreference>("row_number")?.isEnabled = true
                preferenceScreen.findPreference<SeekBarPreference>("col_number")?.isEnabled = true
            }
            "about_btn" -> {
                findNavController().navigate(R.id.action_settings2Fragment_to_aboutFragment)
            }
        }
        return super.onPreferenceTreeClick(preference)
    }

}