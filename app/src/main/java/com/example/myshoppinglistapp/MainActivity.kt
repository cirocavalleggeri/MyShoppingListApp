package com.example.myshoppinglistapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.example.myshoppinglistapp.ui.theme.MyShoppingListAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyShoppingListAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //ShoppingListApp()
                    Navigation()
                   /* LocationSelectionScreen(
                        LocationData("41.12".toDouble(),"12.34".toDouble()),
                        { Log.e("res1","localscreen") }
                    )*/
                }
            }
        }
    }

    @Composable
fun Navigation(){
    val navController= rememberNavController()
    val viewModel:LocationViewModel=viewModel()
    val context= LocalContext.current
    val locationUtils=LocationUtils(context)
    NavHost(navController = navController,
            startDestination ="shoppinglistscreen"
           ) {
        composable("shoppinglistscreen"){
            ShoppingListApp(
                locationUtils = locationUtils,
                viewModel =viewModel ,
                navController =navController ,
                context = context,
                address = viewModel.address.value.firstOrNull()?.formatted_address ?: "No Address"
            )
        }

        dialog("locationscreen"){backstack->
            viewModel.location.value?.let{it1 ->

                LocationSelectionScreen(location = it1, onLocationSelected = {locationdata->
                    viewModel.fetchaddress("${locationdata.latitude},${locationdata.longitude}")
                    // onLocationSelected(newLocation)<=>fetchaddress("${it.latitude},${it.longitude}")}
                    navController.popBackStack()
                })
            }
        }

    }
}
}

