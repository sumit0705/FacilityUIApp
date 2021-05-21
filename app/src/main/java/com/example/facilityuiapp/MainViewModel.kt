package com.example.facilityuiapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.facilityuiapp.repo.Repository
import com.example.facilityuiapp.models.FacilityData
import kotlinx.coroutines.launch

class MainViewModel(private val repo: Repository): ViewModel() {

    val myResponse: MutableLiveData<FacilityData> = MutableLiveData()
    var exhashMap : HashMap<Int, Int> = HashMap()

    fun getFacilityData(){
        viewModelScope.launch {
            val response: FacilityData = repo.getFacilityData()
            myResponse.value = response
            if(myResponse.value!!.exclusions.isNotEmpty()){

                 for(i in myResponse.value!!.exclusions) {
                        val id1 = Integer.parseInt(i[0].options_id)
                        val id2 = Integer.parseInt(i[1].options_id)
                     exhashMap[id1] = id2

                }
            }
        }
    }

    fun getexhashMap(): HashMap<Int,Int> = exhashMap
}