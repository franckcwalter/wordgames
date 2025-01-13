package com.devid_academy.local

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

class Converters {


    /***** STRING LIST CONVERTERS *****/
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return value.joinToString(separator = ",")
    }
    @TypeConverter
    fun toStringList(value: String): List<String> {
        return value.split(",").map { it.trim() }
    }

    /***** UUID CONVERTERS *****/
    @TypeConverter
    fun fromUuid(uuid: UUID): String {
        return uuid.toString()
    }
    @TypeConverter
    fun toUuid(uuidString: String): UUID {
        return UUID.fromString(uuidString)
    }

    /***** LOCAL DATE CONVERTERS *****/
    @TypeConverter
    fun fromLocalDateTime(date: LocalDateTime): String {
        return date.toString()
    }
    @TypeConverter
    fun toLocalDateTime(dateString: String): LocalDateTime {
        return LocalDateTime.parse(dateString)
    }

}
