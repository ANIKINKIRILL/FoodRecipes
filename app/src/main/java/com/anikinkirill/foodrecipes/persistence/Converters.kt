package com.anikinkirill.foodrecipes.persistence

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {

    companion object {
        @TypeConverter
        fun fromArrayList(list: Array<String>) : String {
            val gson = Gson()
            return gson.toJson(list)
        }

        @TypeConverter
        fun fromString(value: String) : Array<String> {
            val gson = Gson()
            val listType: Type = object : TypeToken<Array<String>>(){}.type
            return gson.fromJson(value, listType)
        }

    }

}