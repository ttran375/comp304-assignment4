package app.manohar.roomcrud.Repository

import app.manohar.roomcrud.Models.Users
import app.manohar.roomcrud.RoomDatabase.UserDao

class UserRepo(

    private val userDao: UserDao
) {

    suspend fun insertUser(user: Users) {
        userDao.insertUser(user)
    }

    suspend fun getAllUsers(): List<Users> {
        return userDao.getAllUsers()
    }

    suspend fun updateUser(user: Users) {
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: Users) {
        userDao.deleteUser(user)
    }

    suspend fun deleteAll() {
        userDao.deleteAll()
    }
}