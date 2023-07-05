package com.example.scorekeeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    }
}