package com.example.estacionapp.addParking.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.estacionapp.addParking.ui.model.ParkingModel
import javax.inject.Inject

class ParkingsViewModel @Inject constructor():ViewModel() {
    //LiveData para mostrar el Dialogo
    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog:LiveData<Boolean> = _showDialog
    //Listado para RecyclerView de Parkings
    private val _parkings = mutableStateListOf<ParkingModel>()
    val parkings:List<ParkingModel> = _parkings

    fun onDialogCLose() {
        _showDialog.value = false
    }

    fun onParkingCreated(parking: String) {
        _showDialog.value = false
        _parkings.add(ParkingModel(name = parking))
        Log.i("Parking Creado: ", parking)
    }

    fun onShowDialogClick() {
        _showDialog.value = true
    }

    fun onCheckBoxSelected(parkingModel: ParkingModel) {
        val index = _parkings.indexOf(parkingModel)
        //Sino no refrescaba la lista con los cambios
        _parkings[index] = _parkings[index].let {
            it.copy(selected = !it.selected)
        }
    }

    fun onItemRemove(parkingModel: ParkingModel) {
        val parking = _parkings.find { it.id == parkingModel.id }
        _parkings.remove(parking)
    }


}