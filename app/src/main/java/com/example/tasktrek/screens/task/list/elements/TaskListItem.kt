package com.example.tasktrek.screens.task.list.elements

import android.view.RoundedCorner
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import com.example.tasktrek.ui.theme.colorScheme1
import com.example.tasktrek.ui.theme.colorScheme2
import com.example.tasktrek.ui.theme.colorScheme3

@Composable
fun TaskListItem(
    modifier: Modifier = Modifier,
    taskName:String = "",
    startTime:String = "",
    endTime:String = "",
    timeDiff:Long = 0,
    date:String = "",
    description:String = "",
    colorId: Int = 0) {
    Card(
        colors = CardDefaults.outlinedCardColors(
            when (colorId) {
                0 -> colorScheme1
                1 -> colorScheme2
                else -> colorScheme3
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = CardDefaults.outlinedShape
    ) {
        Text(
            text = taskName,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = startTime, style = MaterialTheme.typography.titleMedium)
                Text(text = "START", style = MaterialTheme.typography.bodySmall)
            }
            Box(
                modifier = Modifier
                    .background(color = Color.Black, shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
                Text(
                    text = formatTimeDiff(timeDiff),
                    color = when (colorId) {
                        0 -> colorScheme1
                        1 -> colorScheme2
                        else -> colorScheme3
                    },
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Column {
                Text(text = endTime, style = MaterialTheme.typography.titleMedium)
                Text(text = "END", style = MaterialTheme.typography.bodySmall)
            }
        }
    }

}

fun formatTimeDiff(timeDiff: Long): String {
    val time = timeDiff/(60*1000)
    val hour = time/60
    val minutes = time%60
    if(time>=60){

        if(hour!=0L){
            return "$hour H $minutes MIN"
        }
    }
    return "$minutes MIN"
}

@Preview
@Composable
private fun TaskListItemPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.Black)
    ) {
        TaskListItem(colorId = 0)
        TaskListItem(colorId = 1)
        TaskListItem(colorId = 2)
    }
}