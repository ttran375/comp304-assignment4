package app.manohar.roomcrud.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "users"
)

data class Users(

    @PrimaryKey(
        autoGenerate = true
    )val id : Int = 0,
    val name: String,
    val age: Int

)
