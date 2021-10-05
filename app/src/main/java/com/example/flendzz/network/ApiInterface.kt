package com.example.flendzz.network


import com.example.flendzz.info_show.model.data_class.data_class
import com.example.flendzz.info_show.model.data_class.data_classItem

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("/users")
    suspend fun getEmpList(): Response<data_class>


    @GET("/users/{id}")
    suspend fun getEmpDetails(
    @Path("id")  id: String
    ): Response<data_classItem>
}