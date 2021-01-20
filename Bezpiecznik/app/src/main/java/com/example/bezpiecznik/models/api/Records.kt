package com.example.bezpiecznik.models.api

import com.example.bezpiecznik.models.entities.Session
import com.google.gson.annotations.SerializedName


class Records(@SerializedName("records") var records: ArrayList<Session>) {
}