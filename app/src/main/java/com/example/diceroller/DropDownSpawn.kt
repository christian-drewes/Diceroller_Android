package com.example.diceroller

import java.util.UUID

class DropDownSpawn (
    id: String = UUID.randomUUID().toString(),
    expanded: Boolean = true,
    selectedItem: String = "Select...",
    items: List<String> = listOf("A", "B", "C") // Could vary per dropdown
)