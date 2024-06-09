package com.example.myshoppinglistapp

import android.Manifest
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController


data class ShoppingItem(val id:Int,
                        var name:String,
                            var quantity:Int,
                        var isEditing:Boolean=false,
                        var address:String="" )

@Composable
fun ShoppingListApp(
    locationUtils: LocationUtils,
    viewModel: LocationViewModel,
    navController:NavController,
    context: Context,
    address: String


){
   // val primoElemento=shoppingListItem(1,"gf",3,true)
    //var itemShop by remember { mutableStateOf(listOf<shoppingListItem>()) }
    var sItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var showDialog: Boolean by remember{ mutableStateOf(false) }
    var itemName: String by remember{ mutableStateOf("") }
    var itemQuantity: String by remember{ mutableStateOf("") }

    //richiesta permessi
        val requestPermissionLauncher= rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions() ,
        onResult ={permissions->
            if((permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true)&&
                (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true)) {
                //Localizzazione possibile
                Toast.makeText(context,"Permessi ottenuti"
                    , Toast.LENGTH_LONG).show()
                locationUtils.requestLocationUpdates(viewModel)
            }else{
                // Spiega perchè vuoi ottenere questi permessi
               // messaggio.value="Non è possibile la localizzazione"
                val rationaleRequired= ActivityCompat.
                shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )||
                        ActivityCompat.
                        shouldShowRequestPermissionRationale(
                            context as MainActivity,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                if(rationaleRequired){
                    Toast.makeText(context,"Questi permessi sono necessari" +
                            "              per poter far funzionare l'applicazione"
                        , Toast.LENGTH_LONG).show()

                }else{
                    // questo succede una volta che l'utente nega i permessi
                    Toast.makeText(context,"Abilita " +
                            " manualmente i permessi dalla impostazione del telefono"
                        , Toast.LENGTH_LONG).show()
                }

            }

        } )
//fine richiesta permessi

    Column(modifier= Modifier.fillMaxSize()
        ,verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
            , horizontalArrangement = Arrangement.Center
        ) {


            Button(
                // modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {

                 showDialog=true}) {
                Text(text = "Aggiungi")
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ){
            items(sItems){item->
                Log.d("LAZY","item.id:${item.id}  it.id:${sItems.map{it.id}} ")
                if (item.isEditing) {

                    ShoppingItemEditor(
                        item = item,
                        onEditComplete = { editedName, editedQuantity ->
                            sItems = sItems.map { it.copy(isEditing = false) }
                            val editedItem = sItems.find { it.id == item.id }
                            editedItem?.let {
                                it.name = editedName
                                it.quantity = editedQuantity
                                it.address = address
                            }
                        }
                    )
                } else {
                    ShoppingListItem(
                        item = item,
                        onEditClick = { sItems = sItems.map { it.copy(isEditing = it.id == item.id) } }

                    ) { sItems = sItems - item }

                }

            }
        }
    }
    if(showDialog){
        AlertDialog(onDismissRequest = {showDialog = false },
                     title= {Text(text = "Aggiungi articolo alla lista")},
                     text = { Column {
                OutlinedTextField(
                    value = itemName,
                    onValueChange = {itemName=it
                    }
                    , singleLine = true
                    , modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                    ,label = {Text("Enter Name")
                    }
                )
                OutlinedTextField(
                    value = itemQuantity,
                    onValueChange = {itemQuantity=it

                    }
                    , singleLine = true
                    , modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                    ,label = {Text("Enter Quantity")
                    }
                )
                 Button(onClick = {
                     if (locationUtils.hasLocationPermission(context)){

                         locationUtils.requestLocationUpdates(viewModel)
                         navController.navigate("locationscreen"){
                                                        this.launchSingleTop
                                                                       }
                     }else{requestPermissionLauncher.launch(arrayOf(
                                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                                    Manifest.permission.ACCESS_COARSE_LOCATION
                     ))}



                      }) { Text(text = "Indirizzo")



                 }                   
            }  },
                    confirmButton = {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            Button(
                                // modifier = Modifier.align(Alignment.CenterHorizontally),
                                onClick = {
                                    if(itemName.isNotBlank()){
                                                  val newItem=ShoppingItem(id = sItems.size+1,
                                                                              name = itemName,
                                                                               quantity = itemQuantity.toInt(),
                                                                               address = address
                                                                              )
                                                         sItems=sItems+newItem
                                                        itemName=""
                                                              }

                                    showDialog=false}) {
                                Text(text = "Add")
                            }
                            Button(
                                // modifier = Modifier.align(Alignment.CenterHorizontally),
                                onClick = {

                                    showDialog=false}) {
                                Text(text = "Cancel")
                            }
                        }

                       }

                                  )

    }
}
@Composable
fun  ShoppingListItem(
    item: ShoppingItem,
    onEditClick:()->Unit,
    onDeleteClick:() ->Unit
              ){
Row (
    modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .border(
            border = BorderStroke(2.dp, Color(0XFF018786)),
            shape = RoundedCornerShape(20)
        ),

    horizontalArrangement = Arrangement.SpaceEvenly

){
   Column(modifier = Modifier
       .weight(1f)
       .padding(8.dp)) {
       Row {
           Text(text =item.name, modifier = Modifier.padding(8.dp))
           Text(text ="Quantity:${item.quantity}", modifier = Modifier.padding(8.dp))
       }
       Row(modifier = Modifier.fillMaxWidth()) {
           Icon(imageVector = Icons.Default.LocationOn, contentDescription ="Posizione" )
           Text(text =item.address)
       }
       }

  Row(  modifier = Modifier.padding(8.dp)) {
      IconButton(onClick = onEditClick) {
          Icon(imageVector = Icons.Default.Edit, contentDescription ="Modifica" )
      }
      IconButton(onClick =onDeleteClick) {
          Icon(imageVector = Icons.Default.Delete, contentDescription ="Cancella" )
      }
  }
}
}
@Composable
fun ShoppingItemEditor(item:ShoppingItem,  onEditComplete:(String,Int)->Unit){
 var editName  by remember {mutableStateOf(item.name) }
 var editQuantity  by remember {mutableStateOf(item.quantity.toString()) }
 var isEditing by remember { mutableStateOf(item.isEditing) }
 Row (modifier = Modifier
     .fillMaxWidth()
     .background(Color.White)
     .padding(8.dp),
      horizontalArrangement = Arrangement.SpaceEvenly //spazio uniforme tra i dati

 ){
        Column {
       BasicTextField(
           value = editName,
           onValueChange = { editName = it },
           singleLine = true,
           modifier = Modifier
               .wrapContentSize()
               .padding(8.dp)
       )

       BasicTextField(
           value = editQuantity,
           onValueChange = {  editQuantity = it },
           singleLine = true,
           modifier = Modifier
               .wrapContentSize()
               .padding(8.dp)
       )
   }




     Button(
         onClick = {
             isEditing = false
            onEditComplete(editName, editQuantity.toIntOrNull() ?: 1)
         }
     ) {
         Text("Save")
     }


 }

}



@Preview
@Composable
fun ShoppingListPreview() {
    //val primoElemento=shoppingListItem(1,"gf",3,true)
   // ShoppingListApp()

}