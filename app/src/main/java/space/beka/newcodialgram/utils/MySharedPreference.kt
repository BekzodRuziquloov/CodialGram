package space.beka.newcodialgram.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import space.beka.newcodialgram.models.User

object MySharedPrefarance {
    private const val NAME = "KeshXotiraga"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }


    var user: User
get() = gsonStringToUser(preferences.getString("user" , "{}")!!)
    set(value) = preferences.edit {
        it.putString("user" , userToString(value) )
    }


    private fun gsonStringToUser(gsonString: String): User {
        return Gson().fromJson<User>(gsonString, User::class.java)
    }

    private fun userToString(user: User): String {
        return Gson().toJson(user)
    }

}