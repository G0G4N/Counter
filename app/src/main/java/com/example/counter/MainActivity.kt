package com.example.counter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.counter.ui.theme.CounterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Make the app full screen (draw behind status bar)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Make the status bar transparent
        window.statusBarColor = android.graphics.Color.TRANSPARENT

        setContent {
            CounterTheme {
                val density = LocalDensity.current
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = with(density) { WindowInsets.navigationBars.getBottom(density).toDp() })
                        .clip(RoundedCornerShape(30.dp)),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CounterApp()
                }
            }
        }
    }
}

@Composable
fun CounterApp() {
    // State for each counter
    var blueCounter by remember { mutableIntStateOf(0) }
    var redCounter by remember { mutableIntStateOf(0) }
    val maxCounterValue = 999999
    val minCounterValue = -999999
    val context = LocalContext.current

    // Function to reset counters
    fun resetCounters() {
        blueCounter = 0
        redCounter = 0
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxSize()) {
                // Blue side
                CounterSide(
                    color = Color.Blue,
                    counter = blueCounter,
                    onIncrement = {
                        if (blueCounter < maxCounterValue) {
                            blueCounter++
                        }
                        if (blueCounter == maxCounterValue) {
                            Toast.makeText(context, "Maximum Value Reached!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    onDecrement = {
                        if (blueCounter > minCounterValue) {
                            blueCounter--
                        }
                        if (blueCounter == minCounterValue) {
                            Toast.makeText(context, "Minimum Value Reached!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                // Red side
                CounterSide(
                    color = Color.Red,
                    counter = redCounter,
                    onIncrement = {
                        if (redCounter < maxCounterValue) {
                            redCounter++
                        }
                        if (redCounter == maxCounterValue) {
                            Toast.makeText(context, "Maximum Value Reached!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    onDecrement = {
                        if (redCounter > minCounterValue) {
                            redCounter--
                        }
                        if (redCounter == minCounterValue) {
                            Toast.makeText(context, "Minimum Value Reached!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        // Reset Button
        Button(
            onClick = { resetCounters() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            )
        ) {
            Text("Reset")
        }
    }
}

@Composable
fun CounterSide(
    color: Color,
    counter: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(color),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        // Top half (increment)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clickable { onIncrement() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+",
                fontSize = 60.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Counter text
        Text(
            text = "$counter",
            fontSize = 48.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
        )

        // Bottom half (decrement)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clickable { onDecrement() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "-",
                fontSize = 60.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CounterAppPreview() {
    CounterTheme {
        CounterApp()
    }
}