package app.manohar.roomcrud

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.text.set
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import app.manohar.roomcrud.Adapters.UserListAdapter
import app.manohar.roomcrud.Models.Users
import app.manohar.roomcrud.Repository.UserRepo
import app.manohar.roomcrud.RoomDatabase.UserDatabase
import app.manohar.roomcrud.ViewModels.UserViewModel
import app.manohar.roomcrud.ViewModels.ViewModelFactory
import app.manohar.roomcrud.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : AppCompatActivity(), UserListAdapter.OnItemClickListener {


    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserListAdapter

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {

            binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

            val repository =
                UserRepo(UserDatabase.getDatabaseInstance(applicationContext).userDao())
            val viewModelFactory = ViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]

            binding.submitBtn.setOnClickListener {

                insertUser()
                loadUsers()
            }

        } catch (e: Exception) {

        }
    }

    override fun onStart() {
        super.onStart()
        loadUsers()
    }

    private fun initRecyclerview() {
        binding.userRecycler.layoutManager = LinearLayoutManager(this@MainActivity)
    }

    private fun loadUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            val users = viewModel.getAllUsers()

            Log.e("Users", ":$users");

            lifecycleScope.launch(Dispatchers.Main) {

                initRecyclerview()
                adapter = UserListAdapter(users,this@MainActivity)
                binding.userRecycler.adapter = adapter
            }
        }
    }

    private fun insertUser() {
        val name = binding.usernameEt.text.toString()
        val age = binding.ageEt.text.toString()


        Log.e("name", ":$name");
        Log.e("age", ":$age");

        val user = Users(name = name, age = age.toInt())

        viewModel.insertUser(user)

        binding.usernameEt.text.clear()
        binding.ageEt.text.clear()
        binding.usernameEt.requestFocus()
    }



    override fun onItemClick(user: Users) {
        // Handle item click here

        //Toast.makeText(this, "Clicked on ${user.id}", Toast.LENGTH_SHORT).show()

        showUserDialog(user)


    }


    private fun showUserDialog(user: Users) {
        val dialogView = layoutInflater.inflate(R.layout.alert_dialog, null)


        val nameEditText = dialogView.findViewById<EditText>(R.id.name_et)
        val ageEditText = dialogView.findViewById<EditText>(R.id.age_et)

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("User Details")

        val alertDialog = dialogBuilder.create()



        nameEditText.setText(user.name)
        ageEditText.setText(user.age.toString())

        dialogView.findViewById<Button>(R.id.cancel_btn).setOnClickListener {
            alertDialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.delete_btn).setOnClickListener {


            viewModel.deleteUser(user)
            loadUsers()


            alertDialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.update_btn).setOnClickListener {
            val name = nameEditText.text.toString()
            val age = ageEditText.text.toString()
            // Process the values here as needed

            val user = Users(id=user.id,name = name, age = age.toInt())
            viewModel.updateUser(user)
            loadUsers()

            alertDialog.dismiss()
        }

        alertDialog.show()
    }


}