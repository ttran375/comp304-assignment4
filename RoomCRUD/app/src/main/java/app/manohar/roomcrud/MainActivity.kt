package app.manohar.roomcrud

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import app.manohar.roomcrud.Adapters.ExerciseListAdapter
import app.manohar.roomcrud.Models.Exercise
import app.manohar.roomcrud.Repository.ExerciseRepository
import app.manohar.roomcrud.RoomDatabase.ExerciseDatabase
import app.manohar.roomcrud.ViewModels.ExerciseViewModel
import app.manohar.roomcrud.ViewModels.ViewModelFactory
import app.manohar.roomcrud.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), ExerciseListAdapter.OnItemClickListener {

    private lateinit var viewModel: ExerciseViewModel
    private lateinit var adapter: ExerciseListAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Initialize the database and repository
        val repository = ExerciseRepository(ExerciseDatabase.getDatabase(this).exerciseDao())
        val viewModelFactory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[ExerciseViewModel::class.java]

        // Set up the submit button click listener
        binding.submitBtn.setOnClickListener {
            insertExercise()
            loadExercises()
        }
    }

    override fun onStart() {
        super.onStart()
        loadExercises()
    }

    // Initialize RecyclerView
    private fun initRecyclerview() {
        binding.userRecycler.layoutManager = LinearLayoutManager(this@MainActivity)
    }

    // Load exercises from the database
    private fun loadExercises() {
        CoroutineScope(Dispatchers.IO).launch {
            val exercises = viewModel.getAllExercises()
            Log.e("Exercises", exercises.toString())

            lifecycleScope.launch(Dispatchers.Main) {
                initRecyclerview()
                adapter = ExerciseListAdapter(exercises, this@MainActivity)
                binding.userRecycler.adapter = adapter
            }
        }
    }

    // Insert a new exercise into the database
    private fun insertExercise() {
        val studentNumber = binding.studentNumberEt.text.toString()
        val exerciseCode = binding.exerciseCodeEt.text.toString()
        val resultObtained = binding.resultObtainedEt.text.toString()
        val marks = binding.marksEt.text.toString().toInt()

        val exercise = Exercise(
            studentNumber = studentNumber,
            exerciseCode = exerciseCode,
            resultObtained = resultObtained,
            marks = marks
        )

        viewModel.insertExercise(exercise)

        // Clear input fields
        binding.studentNumberEt.text.clear()
        binding.exerciseCodeEt.text.clear()
        binding.resultObtainedEt.text.clear()
        binding.marksEt.text.clear()
        binding.studentNumberEt.requestFocus()
    }

    // Handle item click events
    override fun onItemClick(exercise: Exercise) {
        showExerciseDialog(exercise)
    }

    // Display a dialog for updating or deleting an exercise
    private fun showExerciseDialog(exercise: Exercise) {
        val dialogView = layoutInflater.inflate(R.layout.alert_dialog, null)

        val studentNumberEditText = dialogView.findViewById<EditText>(R.id.name_et)
        val exerciseCodeEditText = dialogView.findViewById<EditText>(R.id.age_et)
        val resultObtainedEditText = dialogView.findViewById<EditText>(R.id.resultObtainedEt)
        val marksEditText = dialogView.findViewById<EditText>(R.id.marksEt)

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Exercise Details")

        val alertDialog = dialogBuilder.create()

        studentNumberEditText.setText(exercise.studentNumber)
        exerciseCodeEditText.setText(exercise.exerciseCode)
        resultObtainedEditText.setText(exercise.resultObtained)
        marksEditText.setText(exercise.marks.toString())

        dialogView.findViewById<Button>(R.id.cancel_btn).setOnClickListener {
            alertDialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.delete_btn).setOnClickListener {
            viewModel.deleteExercise(exercise)
            loadExercises()
            alertDialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.update_btn).setOnClickListener {
            val studentNumber = studentNumberEditText.text.toString()
            val exerciseCode = exerciseCodeEditText.text.toString()
            val resultObtained = resultObtainedEditText.text.toString()
            val marks = marksEditText.text.toString().toInt()

            val updatedExercise = Exercise(
                id = exercise.id,
                studentNumber = studentNumber,
                exerciseCode = exerciseCode,
                resultObtained = resultObtained,
                marks = marks
            )

            viewModel.updateExercise(updatedExercise)
            loadExercises()
            alertDialog.dismiss()
        }

        alertDialog.show()
    }
}
