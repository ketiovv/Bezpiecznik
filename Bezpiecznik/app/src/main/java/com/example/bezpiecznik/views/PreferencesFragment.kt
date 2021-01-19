package com.example.bezpiecznik.views

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SeekBarPreference
import com.example.bezpiecznik.R

class PreferencesFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        when(preference?.key){
//            "reset_btn" -> {
//                preferenceScreen.findPreference<SeekBarPreference>("row_number")?.isEnabled = true
//                preferenceScreen.findPreference<SeekBarPreference>("col_number")?.isEnabled = true
//            }
            "about_btn" -> {
                findNavController().navigate(R.id.action_settings2Fragment_to_aboutFragment)
            }
        }
        return super.onPreferenceTreeClick(preference)
    }
}