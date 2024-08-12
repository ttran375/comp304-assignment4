package app.manohar.roomcrud.Repository

import app.manohar.roomcrud.Models.Exercise
import app.manohar.roomcrud.RoomDatabase.ExerciseDao

class ExerciseRepository(private val exerciseDao: ExerciseDao) {

    suspend fun insertExercise(exercise: Exercise) {
        exerciseDao.insertExercise(exercise)
    }

    suspend fun getAllExercises(): List<Exercise> {
        return exerciseDao.getAllExercises()
    }

    suspend fun updateExercise(exercise: Exercise) {
        exerciseDao.updateExercise(exercise)
    }

    suspend fun deleteExercise(exercise: Exercise) {
        exerciseDao.deleteExercise(exercise)
    }

    suspend fun deleteAllExercises() {
        exerciseDao.deleteAllExercises()
    }
}
