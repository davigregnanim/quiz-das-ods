package com.example.quizonline

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.quizonline.databinding.ActivityQuizBinding
import com.example.quizonline.databinding.ScoreDialogBinding

class QuizActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        var questionModelList: List<QuestionModel> = listOf()
        var time: String = ""
        var currentOdsName: String = ""
    }

    lateinit var binding: ActivityQuizBinding
    var currentQuestionIndex = 0
    var selectedAnswer = ""
    var score = 0
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btn0.setOnClickListener(this@QuizActivity)
            btn1.setOnClickListener(this@QuizActivity)
            btn2.setOnClickListener(this@QuizActivity)
            btn3.setOnClickListener(this@QuizActivity)
            nextBtn.setOnClickListener(this@QuizActivity)

            hintBtn.setOnClickListener {
                val odsName = currentOdsName.trim()
                val odsHint = when (odsName.uppercase()) {
                    "ODS 1" -> "ODS 1 – Erradicação da Pobreza"
                    "ODS 2" -> "ODS 2 – Fome Zero e Agricultura Sustentável"
                    "ODS 3" -> "ODS 3 – Saúde e Bem-Estar"
                    "ODS 4" -> "ODS 4 – Educação de Qualidade"
                    "ODS 5" -> "ODS 5 – Igualdade de Gênero"
                    "ODS 6" -> "ODS 6 – Água Potável e Saneamento"
                    "ODS 7" -> "ODS 7 – Energia Limpa e Acessível"
                    "ODS 8" -> "ODS 8 – Trabalho Decente e Crescimento Econômico"
                    "ODS 9" -> "ODS 9 – Indústria, Inovação e Infraestrutura"
                    "ODS 10" -> "ODS 10 – Redução das Desigualdades"
                    "ODS 11" -> "ODS 11 – Cidades e Comunidades Sustentáveis"
                    "ODS 12" -> "ODS 12 – Consumo e Produção Responsáveis"
                    "ODS 13" -> "ODS 13 – Ação Contra a Mudança Global do Clima"
                    "ODS 14" -> "ODS 14 – Vida na Água"
                    "ODS 15" -> "ODS 15 – Vida Terrestre"
                    "ODS 16" -> "ODS 16 – Paz, Justiça e Instituições Fortes"
                    "ODS 17" -> "ODS 17 – Parcerias e Meios de Implementação"
                    else -> "ODS desconhecida"
                }
                Toast.makeText(this@QuizActivity, odsHint, Toast.LENGTH_SHORT).show()
            }
        }

        loadQuestions()
        startTimer()
    }

    private fun startTimer() {
        val totalTimeInMillis = try {
            time.toInt() * 60 * 1000L
        } catch (e: Exception) {
            5 * 60 * 1000L
        }

        timer = object : CountDownTimer(totalTimeInMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60
                binding.timerIndicatorTextview.text =
                    String.format("%02d:%02d", minutes, remainingSeconds)
            }

            override fun onFinish() {
                Toast.makeText(applicationContext, "O tempo acabou!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@QuizActivity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }.start()
    }

    private fun loadQuestions() {
        selectedAnswer = ""

        if (currentQuestionIndex == questionModelList.size) {
            finishQuiz()
            return
        }

        binding.apply {
            questionIndicatorTextview.text =
                "Questão ${currentQuestionIndex + 1}/${questionModelList.size}"
            questionProgressIndicator.progress =
                ((currentQuestionIndex.toFloat() / questionModelList.size.toFloat()) * 100).toInt()

            val currentQuestion = questionModelList[currentQuestionIndex]
            questionTextview.text = currentQuestion.question
            btn0.text = currentQuestion.options[0]
            btn1.text = currentQuestion.options[1]
            btn2.text = currentQuestion.options[2]
            btn3.text = currentQuestion.options[3]

            val defaultColor = getColor(R.color.gray)
            btn0.setBackgroundColor(defaultColor)
            btn1.setBackgroundColor(defaultColor)
            btn2.setBackgroundColor(defaultColor)
            btn3.setBackgroundColor(defaultColor)

            btn0.isEnabled = true
            btn1.isEnabled = true
            btn2.isEnabled = true
            btn3.isEnabled = true
        }
    }

    override fun onClick(view: View?) {
        val clickedBtn = view as Button

        if (clickedBtn.id == R.id.next_btn) {
            if (selectedAnswer.isEmpty()) {
                Toast.makeText(applicationContext, "Selecione uma alternativa para avançar", Toast.LENGTH_SHORT).show()
                return
            }

            currentQuestionIndex++
            loadQuestions()
            return
        }

        val correctAnswer = questionModelList[currentQuestionIndex].correct
        selectedAnswer = clickedBtn.text.toString()

        if (selectedAnswer == correctAnswer) {
            clickedBtn.setBackgroundColor(Color.parseColor("#D80CBF4F"))
            score++
        } else {
            clickedBtn.setBackgroundColor(Color.parseColor("#D8E9270D"))
        }

        binding.apply {
            btn0.isEnabled = false
            btn1.isEnabled = false
            btn2.isEnabled = false
            btn3.isEnabled = false
        }
    }

    private fun finishQuiz() {
        timer?.cancel()
        val totalQuestions = questionModelList.size
        val percentage = ((score.toFloat() / totalQuestions.toFloat()) * 100).toInt()

        val prefs = getSharedPreferences("quiz_progress", MODE_PRIVATE)
        val savedScore = prefs.getInt("${currentOdsName}_score", 0)
        if (score > savedScore) {
            prefs.edit().putInt("${currentOdsName}_score", score).apply()
        }

        val dialogBinding = ScoreDialogBinding.inflate(layoutInflater)
        dialogBinding.apply {
            scoreProgressIndicator.progress = percentage
            scoreProgressText.text = "$percentage%"
            if (percentage > 60) {
                scoreTitle.text = "Parabéns! Você foi ótimo!"
                scoreTitle.setTextColor(Color.BLUE)
            } else {
                scoreTitle.text = "Opa! Tente novamente!"
                scoreTitle.setTextColor(Color.RED)
            }
            scoreSubtitle.text = "$score de $totalQuestions corretas"
            finishBtn.setOnClickListener { finish() }
        }

        AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(false)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}
