package com.example.bezpiecznik.models.api

import com.example.bezpiecznik.models.entities.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface IApiRequest {
    @Headers(
        "Content-Type: application/json",
        "secret-key: $2b$10\$SKWkhv2HZAovsIicIy/61eeFcJGrHgoev6y5zDriR4us.vHiFmRve"
    )
    @POST("b")
    fun addJSon(@Body user: User): Call<User>

    @Headers(
        "secret-key: $2b$10\$SKWkhv2HZAovsIicIy/61eeFcJGrHgoev6y5zDriR4us.vHiFmRve"
    )
    @GET("b/5fdb358c7f44d007fbf98c7a")
    fun getJson() : Call<User>

    @Headers(
        "Content-Type: application/json",
        "secret-key: $2b$10\$SKWkhv2HZAovsIicIy/61eeFcJGrHgoev6y5zDriR4us.vHiFmRve",
        "private: true",
        "collection-id: 5ff8c26361f92720434a5530"
    )
    @POST("b")
    fun addUser(@Body user: User): Call<Response>
}