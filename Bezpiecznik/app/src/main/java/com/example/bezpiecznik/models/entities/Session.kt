package com.example.bezpiecznik.models.entities

import java.time.LocalDateTime

class Session(var startDate: LocalDateTime, var endDate: LocalDateTime, var user: User,
                var attempt: MutableList<Attempt>) {
    fun getTime(){

    }

    fun getPositiveAttempts(){

    }
}