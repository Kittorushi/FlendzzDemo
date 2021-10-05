package com.example.flendzz.network


class LocationRepository(private val loc:ApiInterface) {

    suspend fun getEmpListA() = loc.getEmpList()
    suspend fun getEmpDetailsA(idString: String) = loc.getEmpDetails(idString)
}