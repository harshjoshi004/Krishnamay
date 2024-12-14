package com.example.krishnamaya1.contactAndSupport

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import com.example.krishnamaya1.BoldSubheading
import com.example.krishnamaya1.CommonButton
import com.example.krishnamaya1.CommonButtonElevated
import com.example.krishnamaya1.ui.theme.ElevatedMustard1
import com.example.krishnamaya1.ui.theme.ElevatedMustard2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChantFlow() {
    // List of mantras
    val chantList = listOf(
        "Mahamrityunjaya Mantra" to "ॐ त्र्यम्बकं यजामहे सुगन्धिं पुष्टिवर्धनम्। उर्वारुकमिव बन्धनान्मृत्योर्मुक्षीय मामृतात्॥",
        "Gayatri Mantra" to "ॐ भूर् भुवः स्वः। तत्सवितुर्वरेण्यं। भर्गो देवस्यधीमहि। धियो यो नः प्रचोदयात्॥",
        "Om Mani Padme Hum" to "ॐ मणिपद्मे हूँ",
        "Hare Krishna Mantra" to "हरे कृष्ण हरे कृष्ण कृष्ण कृष्ण हरे हरे। हरे राम हरे राम राम राम हरे हरे।"
    )

    // State to hold selected mantra and chant count
    var selectedchant by remember { mutableStateOf(chantList[0]) }
    var chantCount by remember { mutableStateOf(0) }

    // Dropdown for selecting mantra
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Dropdown menu
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth().animateContentSize()
        ) {
            TextField(
                value = selectedchant.first,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select a Mantra") },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = ElevatedMustard2,
                    unfocusedTextColor = ElevatedMustard2,
                    focusedContainerColor = ElevatedMustard1,
                    unfocusedContainerColor = ElevatedMustard1,
                    focusedIndicatorColor = ElevatedMustard2,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth().animateContentSize()
            ) {
                chantList.forEach { mantra ->
                    DropdownMenuItem(
                        onClick = {
                            selectedchant = mantra
                            expanded = false
                            chantCount = 0
                        }, text = {
                            Text(mantra.first, color = ElevatedMustard2)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Mantra display
        Box(
            modifier = Modifier
                .border(3.dp, ElevatedMustard2, RoundedCornerShape(15))
                .clip(RoundedCornerShape(15))
                .padding(16.dp)
                .fillMaxWidth()
                .animateContentSize()
        ) {
            Text(
                text = selectedchant.second,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = ElevatedMustard2
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Chant count display
        BoldSubheading(
            text = "Chanted: $chantCount times",
            color = ElevatedMustard2,
            modifier = Modifier.padding(8.dp)
        )

        // Chant counter button
        CommonButton(
            text = "Chant",
            onClick = { chantCount++ },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )

        CommonButtonElevated(
            text = "Reset",
            onClick = { chantCount = 0 },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ChantFlow()
}
