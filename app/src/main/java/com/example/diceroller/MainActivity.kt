package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.diceroller.ui.theme.DiceRollerTheme

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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
    var result by remember { mutableStateOf(1) }
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
//        Spacer(modifier = Modifier.height(32.dp))
//        Image(
//            painter = painterResource(imageResource),
//            contentDescription = result.toString()
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(onClick = {result = (1..6).random()}) {
//            Text(stringResource(R.string.roll))
//        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiceDropDownMenu(modifier: Modifier){
    modifier.zIndex(1f)
    // List of dice options
    val diceOptions = listOf("d4", "d6", "d8", "d10", "d12", "d20")

    // State for whether the dropdown menu is expanded or not
    var expanded by remember { mutableStateOf(false) }

    // State for the currently selected dice option
    // Initialize with the first option or leave empty for a placeholder
    var selectedOptionText by remember { mutableStateOf(diceOptions[0]) }
    // Alternatively, for a placeholder initially:
    // var selectedOptionText by remember { mutableStateOf("") }


    // We use ExposedDropdownMenuBox which behaves like a MenuButton dropdown
    // It contains a TextField composable where the selected item is shown
    // and the DropdownMenu below the TextField.
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded
            println("Dropdown expanded state changed to: $expanded")}
    ) {
        // --- Text Field (the visible part when collapsed) ---
        OutlinedTextField(
            readOnly = true, // Make the text field read-only
            value = selectedOptionText,
            onValueChange = {}, // No-op for readOnly
            label = { Text("Select Dice") },
            trailingIcon = {
                // Icon indicates expanded state
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                // Customize colors if needed, e.g., transparent background
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.surface
            ),
        )

        // --- Dropdown Menu (the list that appears) ---
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }, // Close when clicked outside
        ) {
            // Add each dice option as a menu item
            diceOptions.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
//                        selectedOptionText = selectionOption // Update selected text
//                        expanded = false // Close the menu
//                        // --- Optional: Add logic here for when a dice is selected ---
//                        println("Selected dice: $selectionOption")
//                        // Example: you might call a function like onDiceSelected(selectionOption)
//                        // ------------------------------------------------------------
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}