package com.example.test.ui.view.add_note_view

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.test.R

import com.example.test.ui.theme.Orange
import com.example.test.util.ColorCircleClass
import com.example.test.util.UiEvent

import kotlinx.coroutines.flow.collectLatest

var selectedColorValue: Long = 0xFFFFFFFF

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalFoundationApi
@Composable
fun AddNoteScreen(
    viewModel: AddNoteViewModel = hiltViewModel(),
    navController: NavController,
) {
    Log.d("AddNoteScreen", viewModel.title)
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { event ->
            if(event == UiEvent.PopBackStack) {
                navController.navigateUp()
            }
        }
    }

   Scaffold (
       modifier = Modifier
           .fillMaxSize()
           .padding(top = 25.dp, bottom = 35.dp, start = 25.dp, end = 25.dp),

       content ={ padding ->
           Column {
               Icon(
                   painter = painterResource(R.drawable.ic_cancel),
                   contentDescription = null,
                   tint = Orange,
                   modifier = Modifier
                       .size(15.dp)
                       .clickable {
                           navController.popBackStack()
                       }
               )
               Spacer(modifier = Modifier.height(15.dp))
               Details(viewModel = viewModel)
           }
       }
       )
//    Scaffold(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(top = 25.dp, bottom = 35.dp, start = 25.dp, end = 25.dp),
//
//    ) {
//        Column {
//            Icon(
//                painter = painterResource(R.drawable.ic_cancel),
//                contentDescription = null,
//                tint = Orange,
//                modifier = Modifier
//                    .size(15.dp)
//                    .clickable {
//                        navController.popBackStack()
//                    }
//            )
//            Spacer(modifier = Modifier.height(15.dp))
//            Details(viewModel = viewModel)
//        }
//    }

}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Details(viewModel: AddNoteViewModel) {
    val context = LocalContext.current.applicationContext
    var title by remember {
        mutableStateOf(viewModel.title)
    }
    var description by remember {
        mutableStateOf(viewModel.description)
    }
    var isTitleBlank by remember {
        mutableStateOf(false)
    }
    var isDescBlank by remember {
        mutableStateOf(false)
    }
    Log.d("AddNoteScreen", "$title $description")
    val colorsList = listOf<ColorCircleClass>(
        ColorCircleClass(0xFFAEDDEF),
        ColorCircleClass(0xFFFD6348),
        ColorCircleClass(0xFFB57FB3),
        ColorCircleClass(0xFFA29BFE),
        ColorCircleClass(0xFF55EFC4),
        ColorCircleClass(0xFF74B9FF),
        ColorCircleClass(0xFFFFBFBF),
        ColorCircleClass(0xFFB2BEC3)
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Column {
            OutlinedTextField(
                value = viewModel.title,
                onValueChange = {
                    viewModel.onEvent(AddNoteEvent.OnTitleChange(it))
                    isTitleBlank = false
                },
                placeholder = {
                    Text(
                        text = "Title",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.LightGray,
                        fontSize = 20.sp
                    )
                },
                maxLines = 1,
                textStyle = MaterialTheme.typography.headlineLarge,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Orange,
                    unfocusedBorderColor = Color.LightGray,
                    errorBorderColor = Color.Red,
                    errorTrailingIconColor = Color.Red
                ),
                shape = RoundedCornerShape(10),
                isError = isTitleBlank
            )
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxHeight(0.3f),
                value = viewModel.description,
                onValueChange = {
                    viewModel.onEvent(AddNoteEvent.OnDescriptionChange(it))
                    isDescBlank = false
                },
                placeholder = {
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.LightGray,
                        fontSize = 15.sp
                    )
                },
                maxLines = 7,
                textStyle = MaterialTheme.typography.bodyMedium,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = Orange,
                    errorBorderColor = Color.Red,
                    errorTrailingIconColor = Color.Red
                ),
                shape = RoundedCornerShape(10),
                isError = isDescBlank,
            )
        }
        ColorsPalette(colors = colorsList, viewModel = viewModel)
        Box {
            Button(
                onClick = {
                    if (viewModel.title.isNotBlank() && viewModel.description.isNotBlank()) {
                        viewModel.onEvent(AddNoteEvent.SaveNote)
                    } else {
                        Toast.makeText(context, "Empty Fields!", Toast.LENGTH_SHORT).show()
                        isTitleBlank = true
                        isDescBlank = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .clip(RoundedCornerShape(100)),
                colors = ButtonDefaults.buttonColors( Color.Blue)
            ) {
                Text(
                    text = "SAVE",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    fontSize = 15.sp
                )
            }
        }

    }
}

@ExperimentalFoundationApi
@Composable
fun ColorsPalette(colors: List<ColorCircleClass>, viewModel: AddNoteViewModel) {
    var selectedColor by remember {
        mutableStateOf(100)
    }
    LazyVerticalGrid(columns = GridCells.Fixed(4), content ={
        items(colors.size) {item ->
            Box(modifier =
            Modifier.padding(10.dp)
                        .border(
                            width = 2.dp,
                            color = animateColorAsState(
                                if (selectedColor == item) Color.Black else Color.Transparent,
                                label = ""
                            ).value,
                            shape = CircleShape
                        )
                        .clip(CircleShape)
                        .size(40.dp)
                        .background(Color(colors[item].colorValue))
                        .clickable {
                            selectedColor = item
                            selectedColorValue = colors[item].colorValue
                            viewModel.onEvent(AddNoteEvent.OnColorChange(colors[item].colorValue))
                            Log.d("AddNoteScreen", selectedColorValue.toString())
                        }



            )

        }
    }
    )
//
//    LazyVerticalGrid(
//
////        cells = GridCells.Fixed(4),
////        content = {padding ->
////            items(colors.size) { item ->
////                Box(
////                    modifier = Modifier
////                        .padding(10.dp)
////                        .border(
////                            width = 2.dp,
////                            color = animateColorAsState(
////                                if (selectedColor == item) Color.Black else Color.Transparent
////                            ).value,
////                            shape = CircleShape
////                        )
////                        .clip(CircleShape)
////                        .size(40.dp)
////                        .background(Color(colors[item].colorValue))
////                        .clickable {
////                            selectedColor = item
////                            selectedColorValue = colors[item].colorValue
////                            viewModel.onEvent(AddNoteEvent.OnColorChange(colors[item].colorValue))
////                            Log.d("AddNoteScreen", selectedColorValue.toString())
////                        }
////                )
////            }
////        }
//    )
}
