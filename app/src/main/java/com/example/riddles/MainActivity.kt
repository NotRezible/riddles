package com.example.riddles

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.riddles.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val RiddlesList: List<Riddles> = listOf(
        Riddles("Шубу два раза в год снимает. Кто под шубою гуляет?","овца"),
        Riddles("На шесте дворец.\n" +
                "Во дворце певец." ,"скворец"),
        Riddles("Явился в жёлтой шубке:-Прощайте, две скорлупки!","цыплёнок"),
        Riddles("Не ездок, а со шпорами, не сторож, а всех будит.","петух"),
        Riddles("Маленький мальчик всем под ноги смотрит.", "порог"),
        Riddles("Рук и ног у него нет,\n" +
                "А всех трясёт и качает.", "ветер"),
        Riddles("Маленький пузатенький.\n" +
                "А весь дом бережет.","замок"),
        Riddles("Черненька собачка, свернувшись лежит;\n" +
                "Не лает, не кусает.\n" +
                "А в дом не пускает","замок"),
        Riddles("В избу идут — плачут,\n" +
                "из избы идут — пляшут.","ведра"),
        Riddles("Жужжит, жужжит, а с места не улетит","веретено"),
        Riddles("Сяду на хвост, а руками бороду дергаю.","пряжа"),
        Riddles("Без начала, без конца", "кольцо"),
        Riddles("В лесу родился, а в доме хозяйничает", "веник"),
        Riddles("Старая бабка по двору шныряет, чистоту соблюдает.","метла"),
        Riddles("Красная девица росла в темнице,\n" +
                "люди в руки брали, косы срывали.", "морковка"),
        Riddles("Никто не пугает, а вся дрожит.", "осина"),
        Riddles("Малые малышки, зеленые катышки, сквозь землю прошли, на тычинку всползли.", "горох"),
        Riddles("С виду - красная, раскусишь - белая","редиска"),
        Riddles("Стоит кузница – вся в пуговицах.", "черемуха"),
        Riddles("Меня одну не едят, а без меня мало едят", "соль"),
        Riddles("Упадет, разобьется — никакой кузнец не скует.", "яйцо"),
        Riddles("Стоит копытце полно водицы.","колодец"),
        Riddles("Живу я от ветра, сама не ем, а тебе еду готовлю", "мельница"),
        Riddles("Мягок, а не пух,\n" +
                "Зелен, а не трава.","мох"),
        Riddles("Не портной, а всю жизнь с иголками ходит.", "ёж"),
        Riddles("Не прядёт и не ткёт, а людей одевает.", "овца"),
        Riddles("Среди двора стоит копна: спереди вилы, сзади метла", "корова"),
        Riddles("Зелёные глаза - всем мышам гроза.", "кошка"),
        Riddles("Что без крыльев летит и без огня горит?", "солнце"),
        Riddles("Шёл долговяз, в землю увяз.", "дождь")
    )

    var gameList = (RiddlesList.shuffled()).take(10)
    var gameInProgress = false
    var currentRiddleIndex = 0
    var correctAnswers = 0

    lateinit var binding: ActivityMainBinding
    private var launcher: ActivityResultLauncher<Intent>? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupLauncher()
    }

    @SuppressLint("SetTextI18n")
    private fun setupLauncher() {
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val answer = result.data?.getStringExtra("chosenAnswer")
                var message = "";
                val qText = gameList[currentRiddleIndex].riddleText
                if (answer != null) {
                   message = checkAnswer(answer)
                }

                if (currentRiddleIndex == 10)
                {
                    binding.statsButton.isEnabled = true
                    gameInProgress = false
                    binding.riddleButton.text = "Начать"
                }
                binding.riddleButton.isEnabled = true
                binding.answerButton.isEnabled = false
                binding.riddleText.text = "$qText\nВыбранный ответ: $answer\n$message"
            }
        }
    }

    private fun checkAnswer(answer: String): String {
        var message = "Неправильный ответ"
        val correctAnswer = gameList[currentRiddleIndex].correctAnswer
        if (correctAnswer == answer.lowercase().trim()) {
            correctAnswers++
            message = "Правильный ответ"
        }
        currentRiddleIndex++

        return message;
    }

    fun btnNextRiddle(view: View) {
        if (gameInProgress) {
            binding.riddleText.text = gameList[currentRiddleIndex].riddleText
            binding.riddleTextView.text = "Вопрос " + (currentRiddleIndex+1)
            binding.answerButton.isEnabled = true
            binding.riddleButton.isEnabled = false
            binding.riddleButton.text = "Загадка"
        }
        else {
            gameList = (RiddlesList.shuffled()).take(10)
            binding.riddleText.text = gameList[currentRiddleIndex].riddleText
            binding.riddleButton.isEnabled = false
            binding.answerButton.isEnabled = true
            binding.statsButton.isEnabled = false
            gameInProgress = true
            binding.riddleTextView.text = "Вопрос 1"
            currentRiddleIndex = 0
            correctAnswers = 0
        }
    }


    fun btnGetAnswer(view: View) {
        val intent = Intent(this, RiddleActivity::class.java)
        intent.putExtra("question", gameList[currentRiddleIndex].riddleText)
        launcher?.launch(intent)
    }

    fun btnGetStats(view: View) {
        val intent = Intent(this, StatsActivity::class.java)
        intent.putExtra("answerTotal", currentRiddleIndex.toString())
        intent.putExtra("answerCorrect", correctAnswers.toString())
        intent.putExtra("answerWrong", (currentRiddleIndex - correctAnswers).toString())
        startActivity(intent)
    }
}

class Riddles (
    val riddleText: String,
    var correctAnswer: String) {
}
