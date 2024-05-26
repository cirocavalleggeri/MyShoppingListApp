package com.example.myshoppinglistapp

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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

data class shoppingListItem(val id:Int,var name:String,
                            var quantity:Int,var isEditable:Boolean=false)


@Composable
fun shoppingListApp(){
    val primoElemento=shoppingListItem(1,"gf",3,true)
    var itemShop by remember { mutableStateOf(listOf<shoppingListItem>()) }
    var showDialog: Boolean by remember{ mutableStateOf(false) }
    var itemName: String by remember{ mutableStateOf("") }
    var itemQuantity: String by remember{ mutableStateOf("") }
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
            items(itemShop){item->
                     ShowShoppingListItems(item = item, { },{})
                     Log.d("LAZY","LAZY "+item.name)


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
                                                  val newItem=shoppingListItem(id = itemShop.size+1,
                                                                              name = itemName,
                                                                               quantity = itemQuantity.toInt()
                                                                              )
                                                        itemShop=itemShop+newItem
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
fun ShowShoppingListItems(
    item:shoppingListItem,
    OneditClick:()->Unit,
    OndeleteClick:() ->Unit
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
  Text(text =item.name, modifier = Modifier.padding(8.dp))
}
}
@Preview
@Composable
fun ShoppingListPreview() {
    val primoElemento=shoppingListItem(1,"gf",3,true)
    shoppingListApp()

}