package com.example.bezpiecznik.models.api

import com.example.bezpiecznik.models.entities.User
import retrofit2.Call
import retrofit2.http.*

interface IApiRequest {
    @Headers(
        "Content-Type: application/json",
        "secret-key: $2b$10\$SKWkhv2HZAovsIicIy/61eeFcJGrHgoev6y5zDriR4us.vHiFmRve"
    )
    @POST("c")
    fun createUserCollection(@Body name: Response): Call<Response>

    @Headers(
        "secret-key: $2b$10\$SKWkhv2HZAovsIicIy/61eeFcJGrHgoev6y5zDriR4us.vHiFmRve"
    )
    @GET("b/{id}")
    fun getUser(@Path("id") id: String) : Call<User>

    @Headers(
        "Content-Type: application/json",
        "secret-key: $2b$10\$SKWkhv2HZAovsIicIy/61eeFcJGrHgoev6y5zDriR4us.vHiFmRve",
        "private: true",
        "collection-id: 5ff8c26361f92720434a5530"
    )
    @POST("b")
    fun addUser(@Body user: User): Call<Response>
}