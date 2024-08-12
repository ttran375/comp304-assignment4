package app.manohar.roomcrud.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.manohar.roomcrud.Models.Users
import app.manohar.roomcrud.Repository.UserRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepo
) : ViewModel() {



    /*private val _users = MutableStateFlow<List<Users>>(emptyList())
    val users: StateFlow<List<Users>> get() = _users

    fun getAllUsers() : List<Users> {
        viewModelScope.launch {
            val userData = userRepository.getAllUsers()
            _users.value = userData
        }
        return _users.value
    }*/


    suspend fun getAllUsers(): List<Users> {

       return userRepository.getAllUsers();

   }


    fun insertUser(user: Users) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

    fun updateUser(user: Users) {
        viewModelScope.launch {
            userRepository.updateUser(user)
        }
    }

    fun deleteUser(user: Users) {
        viewModelScope.launch {
            userRepository.deleteUser(user)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            userRepository.deleteAll()
        }
    }
}