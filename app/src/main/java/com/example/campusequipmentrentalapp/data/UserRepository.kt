package com.example.campusequipmentrentalapp.data

import com.example.campusequipmentrentalapp.data.EquipmentRepository.equipmentList
import com.example.campusequipmentrentalapp.model.*

class UserRepository {
    val userList: List<User> = listOf(

        User(1,
            "20260010",
            "김민수",
            "컴퓨터소프트웨어학과",
            UserRole.STUDENT),

        User(2,
            "prof01",
            "박교수",
            "컴퓨터소프트웨어학과",
            UserRole.PROFESSOR),

        User(3,
            "assistant01",
            "이조교",
            "컴퓨터소프트웨어학과",
            UserRole.ASSISTANT)

    )

    fun findByLoginId(id:String) : User ?= userList.find{ user -> user.loginId == id}
}