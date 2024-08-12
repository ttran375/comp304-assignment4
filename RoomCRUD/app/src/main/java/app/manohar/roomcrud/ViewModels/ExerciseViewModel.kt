package app.manohar.roomcrud.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.manohar.roomcrud.Models.Exercise
import app.manohar.roomcrud.Repository.ExerciseRepository
import kotlinx.coroutines.launch

class ExerciseViewModel(private val exerciseRepository: ExerciseRepository) : ViewModel() {

    suspend fun getAllExercises(): List<Exercise> {
        return exerciseRepository.getAllExercises()
    }

    fun insertExercise(exercise: Exercise) {
        viewModelScope.launch {
            exerciseRepository.insertExercise(exercise)
        }
    }

    fun updateExercise(exercise: Exercise) {
        viewModelScope.launch {
            exerciseRepository.updateExercise(exercise)
        }
    }

    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch {
            exerciseRepository.deleteExercise(exercise)
        }
    }

    fun deleteAllExercises() {
        viewModelScope.launch {
            exerciseRepository.deleteAllExercises()
        }
    }
}
