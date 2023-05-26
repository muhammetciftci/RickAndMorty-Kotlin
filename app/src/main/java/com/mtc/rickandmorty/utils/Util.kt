package com.mtc.rickandmorty.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Util {
    companion object {
        fun convertDate(date: String): String {
            val date = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)
            val formattedDate = date.format(DateTimeFormatter.ofPattern("d MMMM yyyy, HH:mm:ss"))
            return formattedDate.toString()
        }
    }
}
