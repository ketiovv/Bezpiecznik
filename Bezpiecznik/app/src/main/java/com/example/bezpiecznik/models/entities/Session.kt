package com.example.bezpiecznik.models.entities

import java.time.LocalDateTime

class Session(var startDate: String,
                var attempt: MutableList<Attempt>?, var userId: String) {

}