package com.example.bezpiecznik

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class Settings2Fragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}