package com.example.bezpiecznik.models.entities


class User (val id: String, var name: String, var masterPassword: String,
            val history: MutableList<Session>, var pattern: Pattern?, var settings: Settings ) {
}