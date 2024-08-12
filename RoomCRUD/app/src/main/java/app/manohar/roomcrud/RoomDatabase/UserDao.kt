package app.manohar.roomcrud.RoomDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import app.manohar.roomcrud.Models.Users

@Dao
interface UserDao {

    //create user
    @Insert
    suspend fun insertUser(user: Users)

    //read users
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<Users>

    //update user
    @Update
    suspend fun updateUser(user: Users)

    //delete user
    @Delete
    suspend fun deleteUser(user: Users)

    //deleteAll
    @Query("DELETE FROM users")
    suspend fun deleteAll()
}