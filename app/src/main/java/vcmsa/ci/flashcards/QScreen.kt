package vcmsa.ci.flashcards

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QScreen : AppCompatActivity() {
    private val questions = listOf(
        "The earth is flat." to false,
        "Cleopatra was the pharaoh of Egypt." to true,
        "Hitler did not initiate World War 2." to false,
        "Light travels faster than sound." to true,
        "Mount Everest is the tallest mountain on Earth." to true
    )

    private var currentQuestionIndex = 0
    private var score = 0

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_qscreen)

        val question = findViewById<TextView>(R.id.question)
        val results = findViewById<TextView>(R.id.results)
        val btnTrue = findViewById<Button>(R.id.btnTrue)
        val btnFalse = findViewById<Button>(R.id.btnFalse)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val btnScore = findViewById<Button>(R.id.btnScore)

        fun loadQuestion() {
            if (currentQuestionIndex < questions.size) {
                question.text = questions[currentQuestionIndex].first
                results.text = ""
            } else {
                question.text = "Quiz Finished!"
                results.text = ""
                btnTrue.isEnabled = false
                btnFalse.isEnabled = false
                btnNext.isEnabled = false
            }
        }

        loadQuestion()

        btnTrue.setOnClickListener {
            val correctAnswer = questions[currentQuestionIndex].second
            if (correctAnswer) score++
            results.text = if (correctAnswer) "Correct" else "Incorrect"
        }

        btnFalse.setOnClickListener {
            val correctAnswer = questions[currentQuestionIndex].second
            if (!correctAnswer) score++
            results.text = if (!correctAnswer) "Correct" else "Incorrect"
        }

        btnNext.setOnClickListener {
            currentQuestionIndex++
            loadQuestion()
        }

        btnScore.setOnClickListener {
            val intent = Intent(this, ScoreScreen::class.java)
            intent.putExtra("score", score)
            intent.putExtra("question", ArrayList(questions.map { it.first }))
            intent.putExtra("answers", BooleanArray(questions.size) { questions[it].second })
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main2)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
