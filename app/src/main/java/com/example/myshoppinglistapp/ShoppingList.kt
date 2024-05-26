package com.example.myshoppinglistapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class shoppingListItem(val id:Int,var name:String,
                            var quantity:Int,var isEditable:Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun shoppingListApp(){
    val primoElemento=shoppingListItem(1,"gf",3,true)
    var itemShop by remember { mutableStateOf(listOf<shoppingListItem>(primoElemento)) }
    var showDialog by remember{ mutableStateOf(false) }

    Column(modifier= Modifier.fillMaxSize()
        ,verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            Modifier.fillMaxSize(),
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
            items(itemShop){

            }
        }
    }
    if(showDialog){
        AlertDialog(onDismissRequest = { showDialog = false }) {
           Text(text = "Finestra di AlertDialog")
        }
    }
}