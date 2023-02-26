package com.valkotova.wishboxwithcompose.data.model.responses.calendar

import com.valkotova.wishboxwithcompose.domain.model.common.FileInfo
import java.time.LocalDate

data class AllListsResponseForCalendar(
    val calendar : HashMap<DateForCalendarResponse, List<UserResponseForCalendar>>
){
    fun to_AllResponseForCalendar(id : Int) : AllResponseForCalendar{
        calendar.forEach { dateForCalendarResponse, list ->
            dateForCalendarResponse.hasEvents = list?.isNotEmpty() == true
            list.find { it.id != id}?.let {
                dateForCalendarResponse.hasOthersEvents = true
            }
            list.find { it.id == id}?.let {
                dateForCalendarResponse.hasMyEvents = true
            }
        }
        return AllResponseForCalendar(
            myId = id,
            calendar = calendar
        )
    }
}

data class DateForCalendarResponse(
    val date: LocalDate,
    var hasEvents : Boolean = true,
    var hasMyEvents : Boolean = false,
    var hasOthersEvents : Boolean = false
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DateForCalendarResponse

        if (date != other.date) return false

        return true
    }

    override fun hashCode(): Int {
        return date.hashCode()
    }
}

data class AllResponseForCalendar(
    val myId : Int,
    val calendar : HashMap<DateForCalendarResponse, List<UserResponseForCalendar>>
)

data class UserResponseForCalendar(
    val id : Int,
    val nickname : String,
    val name : String,
    val avatar : FileInfo?,
    val events : List<EventResponseForCalendar>,
) {

}

data class EventResponseForCalendar(
    val id : Int,
    val name : String,
    val date: Long?,
    val preview : FileInfo?,
    val wishes : Int
)