package com.example.campusequipmentrentalapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.campusequipmentrentalapp.model.RentalStatus

@Entity(
    tableName = "rentals"
)
data class RentalEntity (

    // 자동 증가 설정
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    //기자재 번호
    val equipmentId: Int,

    // 신청시 사용자 번호
    val userId: Int,

    //대여 기간
    val rentalDays: Int,

    //대여 목적
    val purpose: String,

    //대여상태 (원래 rentalstatus enumeration 객체가 들어가는데 테이블 안에 객체를 넣을 순 없으니 string으로 넣을 것임)
    val status: String

)