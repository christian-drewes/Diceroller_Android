package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diceroller.ui.theme.DiceRollerTheme
import com.example.diceroller.DropDownSpawn

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiceRollerTheme {
                DiceRollerApp()
            }
        }
    }
}

@Preview
@Composable
fun DiceRollerApp() {

    DiceWithButtonAndImage(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
        .padding(4.dp)
    )
}

@Composable
fun PlusButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector = Icons.Filled.Add,
    contentDescription: String? = "Add" // Provide a meaningful description
) {
    var addDropDown by remember { mutableStateOf(false) }
    val dds = DropDownSpawn()
    Button(
        onClick = { addDropDown = true },
        enabled = enabled
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
            // You could add Text(" Add") here if you want text next to the icon
            // modifier = Modifier.size(ButtonDefaults.IconSize) // Default size
        )
    }
    if (addDropDown) {
        DiceDropDownMenu(Modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
    var result by remember { mutableStateOf(1) }
//    var dropsandbutts by remember { mutableStateOf<@Composable ()-> Unit>(DiceDropDownMenu()) }
    val imageResource = when (result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        DiceDropDownMenu(modifier)
        PlusButton(
            modifier = modifier
        )
//        Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(imageResource),
            contentDescription = result.toString()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {result = (1..6).random()}) {
            Text(stringResource(R.string.roll))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiceDropDownMenu(modifier: Modifier){
    // List of dice options
    val diceOptions = listOf("d4", "d6", "d8", "d10", "d12", "d20")

    // State for whether the dropdown menu is expanded or not
    var expanded by remember { mutableStateOf(false) }

    // State for the currently selected dice option
     var selectedOptionText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                modifier = Modifier.menuAnchor(),
                value = selectedOptionText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
                label = {Text("Select Dice")}
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {expanded = false}
            ) {
                diceOptions.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedOptionText = selectionOption
                            expanded = false
                            println("Selected dice: $selectionOption")
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}