package com.example.tasktrek.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasktrek.ui.theme.border1
import com.example.tasktrek.ui.theme.border2
import com.example.tasktrek.ui.theme.border3
import com.example.tasktrek.ui.theme.colorScheme1
import com.example.tasktrek.ui.theme.colorScheme2
import com.example.tasktrek.ui.theme.colorScheme3

@Composable
fun ColoredBox(
    isSelected: Boolean,
    onClick: () -> Unit,
    color: Color,
    borderColor: Color
) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .clickable { onClick() }
            .background(color = color, shape = CircleShape)
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = borderColor,
                shape = CircleShape
            )
    )
}

@Preview
@Composable
private fun ColoredBoxPreview() {
    var selectedIndex by remember {
        mutableStateOf(-1)
    }

    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ColoredBox(
            isSelected = selectedIndex == 0,
            onClick = { selectedIndex = 0 },
            color = colorScheme1,
            borderColor = border1
        )
        Spacer(modifier = Modifier.width(16.dp))
        ColoredBox(
            isSelected = selectedIndex == 1,
            onClick = { selectedIndex = 1 },
            color = colorScheme2,
            borderColor = border2
        )
        Spacer(modifier = Modifier.width(16.dp))
        ColoredBox(
            isSelected = selectedIndex == 2,
            onClick = { selectedIndex = 2 },
            color = colorScheme3,
            borderColor = border3
        )
    }

}