package com.ngocpv.data.utils

import java.text.SimpleDateFormat
import java.util.*

object DatetimeUtil {
    fun getDateTime(timestamp : Long): String? {
        return try {
            val sdf = SimpleDateFormat("EEE, dd MM yyyy")
            val netDate = Date(timestamp * 1000)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }
}