package com.example.krishnamaya1.mantras

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Start
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MantraList(mantras: List<Mantra>) {
    var expandedMantraIndex by remember { mutableStateOf(-1) } // Track expanded item index

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3D2A4))
            .animateContentSize()
    ) {
        items(mantras.size) { index ->
            val mantra = mantras[index]
            val isExpanded = expandedMantraIndex == index
            Card(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                    .clickable { expandedMantraIndex = if (isExpanded) -1 else index },
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp),
                backgroundColor = Color(0xFFFFF7E1)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp).animateContentSize()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = mantra.mantraStr,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6D4C41),
                            modifier = Modifier.widthIn(max = 200.dp) // Set max width here
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = "Expand",
                            tint = Color(0xFF6D4C41)
                        )
                    }
                    if (isExpanded) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Divider(color = Color(0xFFE0C4A8), thickness = 1.dp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Row {
                            Text(
                                text = "Meaning: ",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF3E2723),
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                            Text(
                                text = mantra.meaning,
                                fontSize = 16.sp,
                                color = Color(0xFF111111),
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }

                        Row {
                            Text(
                                text = "Source: ",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF3E2723),
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                            Text(
                                text = mantra.source,
                                fontSize = 16.sp,
                                color = Color(0xFF111111),
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }

                        Row {
                            Text(
                                text = "Application: ",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF3E2723),
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                            Text(
                                text = mantra.realLifeApplication,
                                fontSize = 16.sp,
                                color = Color(0xFF111111),
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
