package com.example.quizonline

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizonline.databinding.QuizItemRecyclerRowBinding

class QuizListAdapter(private val quizModelList: List<QuizModel>) :
    RecyclerView.Adapter<QuizListAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: QuizItemRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: QuizModel) {
            val context = binding.root.context
            val prefs = context.getSharedPreferences("quiz_progress", Context.MODE_PRIVATE)

            // Recupera progresso salvo
            val savedScore = prefs.getInt("${model.title}_score", 0)
            val totalQuestions = model.questionList.size
            val progressText = if (totalQuestions > 0) "$savedScore/$totalQuestions" else ""

            binding.apply {
                quizTitleText.text = model.title
                quizSubtitleText.text = model.subtitle
                quizTimeText.text = model.time + " min"
                quizProgressText.text = progressText

                root.setOnClickListener {
                    val odsDescription = getOdsDescription(model.title)

                    val dialogView = LayoutInflater.from(context)
                        .inflate(R.layout.dialog_ods_intro, null)

                    val titleText = dialogView.findViewById<TextView>(R.id.dialog_title)
                    val descText = dialogView.findViewById<TextView>(R.id.dialog_description)

                    titleText.text = model.title
                    descText.text = odsDescription

                    val dialog = Dialog(context)
                    dialog.setContentView(dialogView)
                    dialog.setCancelable(false)
                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    val width = (context.resources.displayMetrics.widthPixels * 0.85).toInt()
                    dialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

                    dialogView.findViewById<TextView>(R.id.btn_voltar).setOnClickListener {
                        dialog.dismiss()
                    }

                    dialogView.findViewById<TextView>(R.id.btn_continuar).setOnClickListener {
                        dialog.dismiss()
                        QuizActivity.currentOdsName = model.title ?: "ODS"
                        QuizActivity.questionModelList = model.questionList ?: listOf()
                        QuizActivity.time = model.time ?: "18"

                        val intent = Intent(context, QuizActivity::class.java)
                        context.startActivity(intent)
                    }

                    dialog.show()
                }
            }
        }

        private fun getOdsDescription(odsName: String): String {
            return when (odsName.trim().uppercase()) {
                "ODS 1" -> "Erradicar a pobreza em todas as suas formas, em todos os lugares."
                "ODS 2" -> "Acabar com a fome, alcançar a segurança alimentar e promover a agricultura sustentável."
                "ODS 3" -> "Assegurar uma vida saudável e promover o bem-estar para todos, em todas as idades."
                "ODS 4" -> "Garantir educação inclusiva, equitativa e de qualidade, promovendo oportunidades ao longo da vida."
                "ODS 5" -> "Alcançar a igualdade de gênero e empoderar todas as mulheres e meninas."
                "ODS 6" -> "Garantir disponibilidade e manejo sustentável da água e saneamento para todos."
                "ODS 7" -> "Assegurar o acesso confiável, sustentável e moderno à energia para todos."
                "ODS 8" -> "Promover o crescimento econômico sustentado, o emprego pleno e o trabalho decente para todos."
                "ODS 9" -> "Construir infraestruturas resilientes, promover a industrialização inclusiva e sustentável e fomentar a inovação."
                "ODS 10" -> "Reduzir as desigualdades dentro dos países e entre eles."
                "ODS 11" -> "Tornar as cidades e os assentamentos humanos inclusivos, seguros, resilientes e sustentáveis."
                "ODS 12" -> "Assegurar padrões de consumo e produção sustentáveis."
                "ODS 13" -> "Tomar medidas urgentes para combater a mudança do clima e seus impactos."
                "ODS 14" -> "Conservar e usar de forma sustentável os oceanos, mares e recursos marinhos."
                "ODS 15" -> "Proteger, restaurar e promover o uso sustentável dos ecossistemas terrestres."
                "ODS 16" -> "Promover sociedades pacíficas, justas e inclusivas, com instituições eficazes e responsáveis."
                "ODS 17" -> "Fortalecer os meios de implementação e revitalizar a parceria global para o desenvolvimento sustentável."
                else -> "Conheça mais sobre as metas do desenvolvimento sustentável."
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = QuizItemRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = quizModelList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(quizModelList[position])
    }
}
