package a7m3d.game.quizgame

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var nextButton: AppCompatButton
    private lateinit var exitButton: AppCompatButton
    private lateinit var trueButton: AppCompatButton
    private lateinit var falseButton: AppCompatButton
    private lateinit var quizText: TextView
    private lateinit var quizNumber: TextView
    private lateinit var scoreNumber: TextView
    private lateinit var questionsID: List<Int>
    private val boolChoice = arrayListOf<AppCompatButton>()
    private val poolID = listOf(R.drawable.false_background, R.drawable.true_background)
    private val answers = listOf(1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0)
    private var answer = 0
    private var count = 0
    private var score = 0
    private var answered = false
    private var quizCount = ""
    private var scoreCount = ""
    private var numberText = ""
    private var scoreText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        quizText = findViewById(R.id.quiz_text)
        quizNumber = findViewById(R.id.quiz_number)
        scoreNumber = findViewById(R.id.score_number)

        nextButton = findViewById(R.id.next_button)
        nextButton.setOnClickListener { compareAnswer() }
        exitButton = findViewById(R.id.exit_button)
        exitButton.setOnClickListener { finish() }

        falseButton = findViewById(R.id.false_button)
        boolChoice.add(falseButton)
        falseButton.setOnClickListener { choice(0, 1) }
        trueButton = findViewById(R.id.true_button)
        boolChoice.add(trueButton)
        trueButton.setOnClickListener { choice(1, 0) }

        quizCount = getString(R.string.quiz_count)
        scoreCount = getString(R.string.score_count)
        questionsID = listOf(
            R.string.q_1, R.string.q_2, R.string.q_3, R.string.q_4, R.string.q_5,
            R.string.q_6, R.string.q_7, R.string.q_8, R.string.q_9, R.string.q_10,
            R.string.q_11, R.string.q_12, R.string.q_13, R.string.q_14, R.string.q_15,
            R.string.q_16, R.string.q_17, R.string.q_18, R.string.q_19, R.string.q_20,
            R.string.q_21
        )

        changeText()
    }

    private fun changeText() {
        if (count < questionsID.size) quizText.text = getString(questionsID[count])
        numberText = "$quizCount ${count + 1} - ${answers.size}"
        scoreText = "$scoreCount $score"
        quizNumber.text = numberText
        scoreNumber.text = scoreText
    }

    private fun choice(same: Int, opposite: Int) {
        answer = same
        boolChoice[same].background = ContextCompat.getDrawable(
            this, R.drawable.boolean_button_disabled
        )
        boolChoice[same].setTextColor(Color.BLACK)

        boolChoice[opposite].background = ContextCompat.getDrawable(
            this, poolID[opposite]
        )
        boolChoice[opposite].setTextColor(Color.WHITE)

        nextButton.background = ContextCompat.getDrawable(
            this, R.drawable.active_button
        )
        nextButton.setTextColor(Color.WHITE)
        answered = true
    }

    private fun compareAnswer() {
        if (!answered) {
            Toast.makeText(this, "Answer current question to go to the next",
                Toast.LENGTH_SHORT).show()
            return
        }
        answered = false
        nextButton.background = ContextCompat.getDrawable(
            this, R.drawable.inactive_button
        )
        nextButton.setTextColor(Color.BLACK)
        boolChoice[answer].background = ContextCompat.getDrawable(
            this, poolID[answer]
        )
        boolChoice[answer].setTextColor(Color.WHITE)
        if (answer == answers[count]) score++
        count++
        changeText()
        if (count == answers.size-2) nextButton.text = getString(R.string.final_result)
        if (count == answers.size-1) showFinalResult()
    }

    private fun showFinalResult() {
        val percentage = ((score.toDouble()/((count+1).toDouble()))*100.0).toInt()
        val msg = "Your final result: $percentage%"
        val dialog = AlertDialog.Builder(this)
        dialog.setCancelable(false)
        dialog.setTitle(msg)
        dialog.setNeutralButton("Restart") { _, _ -> recreate() }
        dialog.setPositiveButton("Exit") { _, _ -> finish() }
        dialog.show()
    }
}