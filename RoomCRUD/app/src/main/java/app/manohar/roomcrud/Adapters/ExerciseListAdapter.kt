package app.manohar.roomcrud.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.manohar.roomcrud.Models.Exercise
import app.manohar.roomcrud.R

class ExerciseListAdapter(
    private val exercises: List<Exercise>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ExerciseListAdapter.ExerciseViewModel>() {

    interface OnItemClickListener {
        fun onItemClick(exercise: Exercise)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewModel {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.exercise_layout, parent, false)
        return ExerciseViewModel(view)
    }

    override fun getItemCount() = exercises.size

    override fun onBindViewHolder(holder: ExerciseViewModel, position: Int) {
        holder.bind(exercises[position])
    }

    inner class ExerciseViewModel(exerciseView: View) : RecyclerView.ViewHolder(exerciseView) {

        private val studentNumber: TextView = exerciseView.findViewById(R.id.studentNumber)
        private val exerciseCode: TextView = exerciseView.findViewById(R.id.exerciseCode)
        private val resultObtained: TextView = exerciseView.findViewById(R.id.resultObtained)
        private val marks: TextView = exerciseView.findViewById(R.id.marks)

        fun bind(exercise: Exercise) {
            studentNumber.text = "Student #: ${exercise.studentNumber}"
            exerciseCode.text = "Code: ${exercise.exerciseCode}"
            resultObtained.text = "Result: ${exercise.resultObtained}"
            marks.text = "Marks: ${exercise.marks}"
        }

        init {
            exerciseView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(exercises[position])
                }
            }
        }
    }
}
