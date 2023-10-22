package com.example.riddles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.riddles.databinding.ActivityStatsBinding

class StatsActivity : AppCompatActivity() {
    lateinit var binding: ActivityStatsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        binding = ActivityStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.answerTotalText.text = "Всего ответов: " + intent.getStringExtra("answerTotal")
        binding.answerCorrectText.text = "Правильных ответов: " + intent.getStringExtra("answerCorrect")
        binding.answerWrongText.text = "Неправильных ответов: " + intent.getStringExtra("answerWrong")
    }

    fun btnBack(view: View) {
        finish()
    }
}