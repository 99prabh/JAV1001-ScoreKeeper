package com.example.scorekeeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView

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
        team1ScoreTextView = findViewById(R.id.team1Score)
        team2ScoreTextView = findViewById(R.id.team2Score)
        team1Spinner = findViewById(R.id.scoringSpinnerTeam1)
        team2Spinner = findViewById(R.id.scoringSpinnerTeam2)

        val scoringOptions = listOf("1", "2", "3", "6")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, scoringOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        team1Spinner.adapter = adapter
        team2Spinner.adapter = adapter

        val team1IncreaseButton = findViewById<Button>(R.id.team1IncreaseButton)
        val team1DecreaseButton = findViewById<Button>(R.id.team1DecreaseButton)
        val team2IncreaseButton = findViewById<Button>(R.id.team2IncreaseButton)
        val team2DecreaseButton = findViewById<Button>(R.id.team2DecreaseButton)

    }
}