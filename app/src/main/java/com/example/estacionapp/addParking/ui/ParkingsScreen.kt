package com.example.estacionapp.addParking.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.estacionapp.addParking.ui.model.ParkingModel


@Composable
fun ParkingsScreen(parkingsViewModel: ParkingsViewModel) {
    //LiveData
    val showDialog: Boolean by parkingsViewModel.showDialog.observeAsState(initial = false)

    Box(modifier = Modifier.fillMaxSize()) {
        AddParkingsDialog(showDialog,
            onDismiss = { parkingsViewModel.onDialogCLose() },
            onParkingAdded = { parkingsViewModel.onParkingCreated(it) })
        FabDialog(
            Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp), parkingsViewModel
        )
        ParkingList(parkingsViewModel)
    }
}

@Composable
fun ParkingList(parkingsViewModel: ParkingsViewModel) {
    //RecyclerView de Parkings
    val myParkings: List<ParkingModel> = parkingsViewModel.parkings
    LazyColumn {
        items(myParkings, key = { it.id }) { parking ->
            ItemParking(parking, parkingsViewModel)
        }
    }
}

@Composable
fun ItemParking(parkingModel: ParkingModel, parkingsViewModel: ParkingsViewModel) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {
                    parkingsViewModel.onItemRemove(parkingModel)
                })
            }, elevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = parkingModel.name, modifier = Modifier.weight(1f)
            )
            Checkbox(
                checked = parkingModel.selected,
                onCheckedChange = { parkingsViewModel.onCheckBoxSelected(parkingModel) })
        }
    }
}

@Composable
fun FabDialog(modifier: Modifier, parkingsViewModel: ParkingsViewModel) {
    FloatingActionButton(onClick = {
        //mostrar dialogo
        parkingsViewModel.onShowDialogClick()
    }, modifier = modifier) {
        Icon(Icons.Filled.Add, contentDescription = "Agregar")
    }
}

@Composable
fun AddParkingsDialog(show: Boolean, onDismiss: () -> Unit, onParkingAdded: (String) -> Unit) {
    var myParking by remember { mutableStateOf("") }
    //AGREGAR EL OBJETO PARKING
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Nombre del Estacionamiento:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    value = myParking,
                    onValueChange = { myParking = it },
                    singleLine = true,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.size(16.dp))
                Button(onClick = {
                    onParkingAdded(myParking)
                    myParking = ""
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Añadir Estacionamiento")
                }
            }
        }
    }
}