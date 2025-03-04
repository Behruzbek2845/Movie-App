package com.behruzbek0430.movieapp.SharedPraferance

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object MySharedPraferance {
    private const val NAME = "catch_file_name"
    private const val MODE = Context.MODE_PRIVATE

    private lateinit var preferences: SharedPreferences

    fun init(context: Context){
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation:(SharedPreferences.Editor) -> Unit){
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var sharedList: ArrayList<String>
        get() = gsonStringToList(preferences.getString("sa", "[]")!!)
        set(value) = preferences.edit {
            it.putString("sa", listToGsonString(value))
        }

    var keyList: String = ""

    private fun gsonStringToList(gsonString: String): ArrayList<String>{
        val list = ArrayList<String>()
        val type = object : TypeToken<ArrayList<String>>() {}.type
        list.addAll(Gson().fromJson(gsonString, type))
        return list
    }

    private fun listToGsonString(list:ArrayList<String>): String{
        return Gson().toJson(list)
    }

}