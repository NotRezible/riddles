package com.example.riddles


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.riddles.databinding.ActivityRiddleBinding


class RiddleActivity : AppCompatActivity() {
    lateinit var binding: ActivityRiddleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riddle)
        binding = ActivityRiddleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bRiddleTextView.text = intent.getStringExtra("question")
    }



    fun btnCheckAnswer(view: View) {
        val chosenValue = findViewById<View>(R.id.answerText) as EditText
        if (chosenValue.text.toString().isEmpty()) chosenValue.error = "Ответ не может быть пустым!"
        if(chosenValue.text.toString() == "") {
            binding.answerText.requestFocus()
            return
        }
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("chosenAnswer", chosenValue.text.toString())
        setResult(RESULT_OK, intent)
        finish()
    }
}