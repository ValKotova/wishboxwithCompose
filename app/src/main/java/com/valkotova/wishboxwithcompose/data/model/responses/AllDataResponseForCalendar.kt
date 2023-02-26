package com.valkotova.wishboxwithcompose.data.model.responses

import com.valkotova.wishboxwithcompose.data.model.common.FileInfoData
import com.valkotova.wishboxwithcompose.data.model.responses.calendar.AllListsResponseForCalendar
import com.valkotova.wishboxwithcompose.data.model.responses.calendar.DateForCalendarResponse
import com.valkotova.wishboxwithcompose.data.model.responses.calendar.EventResponseForCalendar
import com.valkotova.wishboxwithcompose.data.model.responses.calendar.UserResponseForCalendar
import com.valkotova.wishboxwithcompose.domain.model.common.to_FileInfo
import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.ZoneId

@Serializable
data class AllDataResponseForCalendar(
    val calendar : HashMap<Long, List<UserDataResponseForCalendar>>
){
    fun to_AllResponseForCalendar() : AllListsResponseForCalendar {
        val map = HashMap<DateForCalendarResponse, List<UserResponseForCalendar>>()
        calendar.forEach { key, userList ->
            map.put(DateForCalendarResponse(Instant.ofEpochMilli(key).atZone(ZoneId.of("UTC")).toLocalDate()), userList.map { userData ->
                userData.to_UserResponseForCalendar()
            })
        }

        return AllListsResponseForCalendar(
            calendar = map)
    }
}

@Serializable
data class UserDataResponseForCalendar(
    val id : Int,
    val nickname : String,
    val name : String,
    val avatar : FileInfoData?,
    val lists : List<EventDataResponseForCalendar>
){
    fun to_UserResponseForCalendar() =
        UserResponseForCalendar(
            id = id,
            nickname = nickname,
            name = name,
            avatar = avatar?.to_FileInfo(),
            events = lists.map {
                it.to_EventResponseForCalendar()
            }
        )
}

@Serializable
data class EventDataResponseForCalendar(
    val id : Int,
    val name : String,
    val data: Long?,
    val preview : FileInfoData?,
    val wishes : Int
){
    fun to_EventResponseForCalendar() =
        EventResponseForCalendar(
            id = id,
            name = name,
            date = data,
            preview = preview?.to_FileInfo(),
            wishes = wishes
        )
}