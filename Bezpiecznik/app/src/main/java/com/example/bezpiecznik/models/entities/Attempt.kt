package com.example.bezpiecznik.models.entities

import com.example.bezpiecznik.models.enums.PatternStrength

class Attempt (var pattern: String, var strength: String, var rows: Int, var columns: Int ) {

    override fun toString(): String {
        return "Pattern: $pattern Strength: $strength Size: $rows x $columns"

    }
}