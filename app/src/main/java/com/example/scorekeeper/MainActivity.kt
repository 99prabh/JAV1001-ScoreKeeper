package com.example.scorekeeper

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.scorekeeper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var team1Score = 0
    private var team2Score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Set up the scoring options in the spinner
        val scoringOptions = listOf("1", "2", "3", "6")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, scoringOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.scoringSpinnerTeam1.adapter = adapter
        binding.scoringSpinnerTeam2.adapter = adapter

        // Handle the click events for the buttons
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

    // Increase Team 1 score by the given score value
    private fun increaseTeam1Score(score: Int) {
        team1Score += score
        updateTeam1ScoreText()
    }

    // Handles decreasing the Team 1 score by the given score value, ensuring it doesn't go below zero
    private fun decreaseTeam1Score(score: Int) {
        if (team1Score >= score) {
            team1Score -= score
            updateTeam1ScoreText()
        } else {
            // If the score goes below zero, set it to zero and show a message to the user
            team1Score = 0
            showToast("Team 1 score cannot go below zero!")
        }
    }

    // Increase Team 2 score by the given score value
    private fun increaseTeam2Score(score: Int) {
        team2Score += score
        updateTeam2ScoreText()
    }

    // Handles decreasing the Team 2 score by the given score value, ensuring it doesn't go below zero
    private fun decreaseTeam2Score(score: Int) {
        if (team2Score >= score) {
            team2Score -= score
            updateTeam2ScoreText()
        } else {
            // If the score goes below zero, set it to zero and show a message to the user
            team2Score = 0
            showToast("Team 2 score cannot go below zero!")
        }
    }

    // Shows a short toast message on the screen
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
