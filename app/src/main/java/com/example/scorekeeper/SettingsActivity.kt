package com.example.scorekeeper

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the layout for the activity using the layout resource "settings_activity"
        setContentView(R.layout.settings_activity)

        if (savedInstanceState == null) {
            // Replace the placeholder FrameLayout with the SettingsFragment
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }

        // Enable the "Up" button on the action bar to navigate back
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Inner class representing the settings fragment
    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            // Load preferences from the XML resource "root_preferences"
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            // Find the SwitchPreferenceCompat using its key "save_scores_preference"
            val saveScoresPreference = findPreference<SwitchPreferenceCompat>("save_scores_preference")

            // Define a listener for changes to the save scores preference
            saveScoresPreference?.setOnPreferenceChangeListener { _, newValue ->
                // Cast the new value to a Boolean
                val shouldSaveScores = newValue as Boolean

                // Access the shared preferences and update the "save_scores" value
                val sharedPreferences = requireContext().getSharedPreferences("scorekeeper", Context.MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putBoolean("save_scores", shouldSaveScores)
                    apply()
                }

                // Indicate that the preference change was handled successfully
                true
            }
        }
    }

    // Override onOptionsItemSelected to handle action bar item clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            // Handle the "Up" button click by finishing the activity
            finish()
            return true
        }
        // If the clicked item is not handled here, let the super class handle it
        return super.onOptionsItemSelected(item)
    }
}
