package com.example.exampleone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun MainScreen(){

    val isExpandedFrom = remember {
        mutableStateOf(false)
    }

    val isExpandedTo = remember {
        mutableStateOf(false)
    }

    val valueFrom = remember{
        mutableStateOf("")
    }

    val openFromButtonText = remember {
        mutableStateOf("From")
    }

    val openToButtonText = remember {
        mutableStateOf("To")
    }

    val result = remember {
        mutableDoubleStateOf(0.0)
    }

    val inFactor = remember {
        mutableDoubleStateOf(0.0)
    }

    val outFactor = remember {
        mutableDoubleStateOf(0.0)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = valueFrom.value,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = {Text("Значение")},
            onValueChange = { value ->
                valueFrom.value = value
                if (openToButtonText.value.equals("To", ignoreCase = true) && openFromButtonText.value.equals("From", ignoreCase = true)){
                    return@OutlinedTextField
                }
                val doubleValue = valueFrom.value.toDouble()
                result.doubleValue = convertion(doubleValue, inFactor.doubleValue, outFactor.doubleValue)
            }
        )

        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    isExpandedFrom.value = true
                }
            ) {
                Text(openFromButtonText.value)
                if (
                    isExpandedFrom.value
                ){
                    DropDownMenu(
                        onDismiss = {
                            isExpandedFrom.value = false
                        },
                        onClick = { name, factor ->
                            openFromButtonText.value = name
                            inFactor.doubleValue = factor
                            isExpandedFrom.value = false
                        }
                    )
                }
            }

            Icon(
                modifier = Modifier.padding(horizontal = 20.dp),
                imageVector = Icons.Default.PlayArrow,
                contentDescription = ""
            )

            Button(
                onClick = {
                    isExpandedTo.value = true
                }
            ) {
                Text(openToButtonText.value)
                if (
                    isExpandedTo.value
                ){
                    DropDownMenu(
                        onDismiss = {
                            isExpandedTo.value = false
                        },
                        onClick = { name, factor ->
                            openToButtonText.value = name
                            outFactor.doubleValue = factor
                            isExpandedTo.value = false
                        }
                    )
                }
            }
        }

        Text("Результат: ${result.doubleValue}")
    }
}


fun convertion(inputValue: Double, conversionFactor: Double, oConvertionFactor: Double): Double{
    val result = (inputValue * conversionFactor * 100.0/ oConvertionFactor).roundToInt() / 100.0
    return result
}

@Composable
fun DropDownMenu(
    onDismiss: () -> Unit,
    onClick: (String, Double) -> Unit
){
    DropdownMenu(
        expanded = true,
        onDismissRequest = {
            onDismiss()
        }
    ) {
        DropdownMenuItem(
            text = { Text("CM") },
            onClick = { onClick("CM", 0.01) }
        )
        DropdownMenuItem(
            text = {
                Text("M")
                   },
            onClick = {
                onClick("M", 1.0)
            }
        )
    }
}

@Preview(showBackground = false)
@Composable
fun Preview(){
    MainScreen()
}