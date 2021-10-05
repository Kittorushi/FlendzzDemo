package com.example.flendzz.info_show.ui.allmatches

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flendzz.info_show.model.data_class.data_class
import com.example.flendzz.network.LocationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EmpListViewModel (var context: Application, var loc: LocationRepository) : EmpListBaseViewModel(context) {


    private val _loadingState = MutableLiveData<String>()

    private val _data = MutableLiveData<data_class>()
    val data: LiveData<data_class>
        get() = _data


    init {
        CoroutineScope(Dispatchers.IO).launch{
            fetchData()
        }

    }



    private suspend fun fetchData() {
        val response = loc.getEmpListA()
        if (response.isSuccessful){
            _data.postValue(response.body())
            Log.d("TAG", "setLiveDataListeners:==   $response")
            Log.d("TAG", "setLiveDataListeners:==   ${response.body()}")
        }else{
            _loadingState.postValue(response.message())
        }
    }

}