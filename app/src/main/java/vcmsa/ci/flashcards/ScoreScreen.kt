package vcmsa.ci.flashcards

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ScoreScreen : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_score)

        val edtNum = findViewById<EditText>(R.id.edtNum)
        val review = findViewById<TextView>(R.id.review)
        val btnReview = findViewById<Button>(R.id.btnReview)
        val btnExit = findViewById<Button>(R.id.btnExit)

        val score = intent.getIntExtra("score", 0)
        val questions = intent.getStringArrayListExtra("questions") ?: arrayListOf()
        val answers = intent.getBooleanArrayExtra("answers") ?: BooleanArray(5)
        val userAnswers = intent.getBooleanArrayExtra("userAnswers") ?: BooleanArray(5)

        edtNum.setText("Your Score: $score/5")

        btnReview.setOnClickListener {
            val reviewText = StringBuilder()
            for (i in questions.indices) {
                reviewText.append("${i + 1}. ${questions[i]}\nCorrect Answer: ${if (answers[i]) "True" else "False"}\n\n")
            }
            review.text = reviewText.toString()
        }



        btnExit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btnExit)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
