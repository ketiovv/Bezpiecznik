package com.example.bezpiecznik.models.api

import com.google.gson.annotations.SerializedName

data class Response( @SerializedName(value = "name", alternate = arrayOf("id"))  var name: String ) {
}