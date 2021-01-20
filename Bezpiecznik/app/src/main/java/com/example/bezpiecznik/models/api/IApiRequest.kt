package com.example.bezpiecznik.models.api

import com.example.bezpiecznik.models.entities.Session
import com.example.bezpiecznik.models.entities.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface IApiRequest {
    @Headers(
        "Content-Type: application/json",
        "secret-key: $2b$10\$SKWkhv2HZAovsIicIy/61eeFcJGrHgoev6y5zDriR4us.vHiFmRve",
        "private: true",
        "collection-id: 60073c581c1ce6535a0f1b64"
    )
    @POST("b")
    fun createUserCollection(@Body records: Records): Call<Response>

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


    @Headers(
        "Content-Type: application/json",
        "secret-key: $2b$10\$SKWkhv2HZAovsIicIy/61eeFcJGrHgoev6y5zDriR4us.vHiFmRve",
        "private: true",
        "collection-id: 60073c581c1ce6535a0f1b64",
        "versioning: false"
    )
    @PUT("b/{id}")
    fun addSession(@Path("id") id: String, @Body records: Records): Call<Response>

    @Headers(
        "Content-Type: application/json",
        "secret-key: $2b$10\$SKWkhv2HZAovsIicIy/61eeFcJGrHgoev6y5zDriR4us.vHiFmRve"
    )
    @GET("b/{id}")
    fun getUserCollection(@Path("id") id: String): Call<Records>
}