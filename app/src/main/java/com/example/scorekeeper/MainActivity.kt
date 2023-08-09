package com.example.scorekeeper

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.example.scorekeeper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Initialize view binding and score variables
    private lateinit var binding: ActivityMainBinding
    private var team1Score = 0
    private var team2Score = 0
    private var shouldSaveScores = false

    // Save scores on configuration changes
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("team1Score", team1Score)
        outState.putInt("team2Score", team2Score)
    }

    // Restore scores from saved instance state
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        team1Score = savedInstanceState.getInt("team1Score", 0)
        team2Score = savedInstanceState.getInt("team2Score", 0)
        updateTeam1ScoreText()
        updateTeam2ScoreText()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Retrieve night mode preference from SharedPreferences
        val sharedPreferences = getSharedPreferences("scorekeeper", Context.MODE_PRIVATE)
        val nightMode = sharedPreferences.getInt("night_mode", AppCompatDelegate.MODE_NIGHT_NO)
        AppCompatDelegate.setDefaultNightMode(nightMode)

        super.onCreate(savedInstanceState)

        // Initialize view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Retrieve saved scores if necessary
        shouldSaveScores = sharedPreferences.getBoolean("save_scores", false)
        if (shouldSaveScores) {
            team1Score = sharedPreferences.getInt("team1Score", 0)
            team2Score = sharedPreferences.getInt("team2Score", 0)
            updateTeam1ScoreText()
            updateTeam2ScoreText()
        }

        // Set up scoring options in the spinner
        val scoringOptions = listOf("1", "2", "3", "6")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, scoringOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.scoringSpinnerTeam1.adapter = adapter
        binding.scoringSpinnerTeam2.adapter = adapter

        // Set click listeners for score buttons
        binding.team1IncreaseButton.setOnClickListener {
            val score = binding.scoringSpinnerTeam1.selectedItem.toString().toInt()
            increaseTeam1Score(score)
        }

        binding.team1DecreaseButton.setOnClickListener {
            val score = binding.scoringSpinnerTeam1.selectedItem.toString().toInt()
            decreaseTeam1Score(score)
        }

        binding.team2IncreaseButton.setOnClickListener {
            val score = binding.scoringSpinnerTeam2.selectedItem.toString().toInt()
            increaseTeam2Score(score)
        }

        binding.team2DecreaseButton.setOnClickListener {
            val score = binding.scoringSpinnerTeam2.selectedItem.toString().toInt()
            decreaseTeam2Score(score)
        }
    }

    // Create options menu and handle night mode switch
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the options menu layout
        menuInflater.inflate(R.menu.menu_main, menu)
        // Find the night mode toggle switch in the menu
        val nightModeItem = menu?.findItem(R.id.action_toggle_daynight_mode)
        val nightModeSwitch = nightModeItem?.actionView as? SwitchCompat
        // Set the switch state based on the current night mode
        nightModeSwitch?.isChecked = (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)

        // Add a listener to the night mode switch to update the night mode setting
        nightModeSwitch?.setOnCheckedChangeListener { _, isChecked ->
            // Determine the new night mode based on the switch state
            val newNightMode = if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            // Apply the new night mode setting
            AppCompatDelegate.setDefaultNightMode(newNightMode)

            // Save the new night mode setting and team scores to SharedPreferences
            val sharedPreferences = getSharedPreferences("scorekeeper", Context.MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                putInt("night_mode", newNightMode)
                putInt("team1Score", team1Score) // Save Team 1 score
                putInt("team2Score", team2Score) // Save Team 2 score
                apply()
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    // Handle options menu item clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_about -> {
                // Show a Toast with developer information
                showToast("Developer: Prabhjot Singh Jolly\nCourse Code: JAVA1001")
                return true
            }
            R.id.action_settings -> {
                // Navigate to SettingsActivity
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    // Increase Team 1 score and handle saving
    private fun increaseTeam1Score(score: Int) {
        team1Score += score
        updateTeam1ScoreText()
        if (shouldSaveScores) {
            saveScores()
        }
    }

    // Decrease Team 1 score and handle saving
    private fun decreaseTeam1Score(score: Int) {
        if (team1Score >= score) {
            team1Score -= score
            updateTeam1ScoreText()
            if (shouldSaveScores) {
                saveScores()
            }
        } else {
            // Show a message if trying to decrease below zero
            team1Score = 0
            showToast("Team 1 score cannot go below zero!")
        }
    }

    // Increase Team 2 score and handle saving
    private fun increaseTeam2Score(score: Int) {
        team2Score += score
        updateTeam2ScoreText()
        if (shouldSaveScores) {
            saveScores()
        }
    }

    // Decrease Team 2 score and handle saving
    private fun decreaseTeam2Score(score: Int) {
        if (team2Score >= score) {
            team2Score -= score
            updateTeam2ScoreText()
            if (shouldSaveScores) {
                saveScores()
            }
        } else {
            // Show a message if trying to decrease below zero
            team2Score = 0
            showToast("Team 2 score cannot go below zero!")
        }
    }

    // Save scores to SharedPreferences
    private fun saveScores() {
        val sharedPreferences = getSharedPreferences("scorekeeper", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt("team1Score", team1Score)
            putInt("team2Score", team2Score)
            apply()
        }
    }

    // Show a short toast message on the screen
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Update the displayed Team 1 score on the UI
    private fun updateTeam1ScoreText() {
        binding.team1Score.text = team1Score.toString()
    }

    // Update the displayed Team 2 score on the UI
    private fun updateTeam2ScoreText() {
        binding.team2Score.text = team2Score.toString()
    }
}
