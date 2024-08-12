package app.manohar.roomcrud.RoomDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import app.manohar.roomcrud.Models.Exercise

@Dao
interface ExerciseDao {

    @Insert
    suspend fun insertExercise(exercise: Exercise)

    @Query("SELECT * FROM exercises")
    suspend fun getAllExercises(): List<Exercise>

    @Update
    suspend fun updateExercise(exercise: Exercise)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    @Query("DELETE FROM exercises")
    suspend fun deleteAllExercises()
}
