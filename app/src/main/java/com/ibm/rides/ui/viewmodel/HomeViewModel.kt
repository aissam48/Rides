package com.ibm.rides.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibm.rides.core.utils.Resource
import com.ibm.rides.remote.dto.VehicleModel
import com.ibm.rides.remote.dto.VehicleModelItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    private val _uiFlowRides = MutableSharedFlow<Resource<VehicleModel>>()
    val uiFlowRides = _uiFlowRides.asSharedFlow()
    fun onGetRides(params: HashMap<String, Any>) {
        viewModelScope.launch {
            homeRepository.onGetRides(params).onEach { result ->
                _uiFlowRides.emit(result)
            }.launchIn(this)
        }
    }

    private val _uiFlowShareData = MutableSharedFlow<VehicleModelItem>()
    val uiFlowShareData = _uiFlowShareData.asSharedFlow()
    fun shareData(value: VehicleModelItem) {
        viewModelScope.launch {
            _uiFlowShareData.emit(value)
        }
    }

    private val _uiFlowBack = MutableSharedFlow<Any>()
    val uiFlowBack = _uiFlowBack.asSharedFlow()
    fun back() {
        viewModelScope.launch {
            _uiFlowBack.emit("")
        }
    }

}