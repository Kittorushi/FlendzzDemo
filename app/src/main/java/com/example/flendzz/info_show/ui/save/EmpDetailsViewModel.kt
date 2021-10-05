package com.example.flendzz.info_show.ui.save

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flendzz.info_show.model.data_class.data_class
import com.example.flendzz.info_show.model.data_class.data_classItem



import com.example.flendzz.network.LocationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmpDetailsViewModel(var context: Application, var loc: LocationRepository) : EmpDetailsBaseViewModel(context) {


    private val _loadingState = MutableLiveData<String>()

    private val _data = MutableLiveData<data_classItem>()
    val data: LiveData<data_classItem>
        get() = _data





    fun fetchData(idPass: String) {

        CoroutineScope(Dispatchers.IO).launch{
        val response = loc.getEmpDetailsA(idPass)
        if (response.isSuccessful){

            _data.postValue(response.body())
        }else{
            _loadingState.postValue("No Values")
        }
        }
    }


}