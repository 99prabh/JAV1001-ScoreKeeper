package com.example.scorekeeper

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var team1ScoreTextView: TextView
    private lateinit var team2ScoreTextView: TextView
    private lateinit var team1Spinner: Spinner
    private lateinit var team2Spinner: Spinner
    private var team1Score = 0
    private var team2Score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        team1ScoreTextView = findViewById(R.id.team1Score)
        team2ScoreTextView = findViewById(R.id.team2Score)
        team1Spinner = findViewById(R.id.scoringSpinnerTeam1)
        team2Spinner = findViewById(R.id.scoringSpinnerTeam2)

        // Set up the scoring options in the spinner
        val scoringOptions = listOf("1", "2", "3", "6")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, scoringOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        team1Spinner.adapter = adapter
        team2Spinner.adapter = adapter

        // Handle the click events for the buttons
        val team1IncreaseButton = findViewById<Button>(R.id.team1IncreaseButton)
        val team1DecreaseButton = findViewById<Button>(R.id.team1DecreaseButton)
        val team2IncreaseButton = findViewById<Button>(R.id.team2IncreaseButton)
        val team2DecreaseButton = findViewById<Button>(R.id.team2DecreaseButton)

        // Increase Team 1 score when increase button is clicked
        team1IncreaseButton.setOnClickListener {
            val score = team1Spinner.selectedItem.toString().toInt()
            increaseTeam1Score(score)
        }

        // Decrease Team 1 score when decrease button is clicked
        team1DecreaseButton.setOnClickListener {
            val score = team1Spinner.selectedItem.toString().toInt()
            decreaseTeam1Score(score)
        }

        // Increase Team 2 score when increase button is clicked
        team2IncreaseButton.setOnClickListener {
            val score = team2Spinner.selectedItem.toString().toInt()
            increaseTeam2Score(score)
        }

        // Decrease Team 2 score when decrease button is clicked
        team2DecreaseButton.setOnClickListener {
            val score = team2Spinner.selectedItem.toString().toInt()
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
        team1ScoreTextView.text = team1Score.toString()
    }

    // Update the displayed Team 2 score on the UI
    private fun updateTeam2ScoreText() {
        team2ScoreTextView.text = team2Score.toString()
    }

}
