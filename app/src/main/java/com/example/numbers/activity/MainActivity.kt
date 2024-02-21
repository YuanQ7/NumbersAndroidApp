package com.example.numbers.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.numbers.models.NumbersViewModel
import com.example.numbers.ui.theme.NumbersTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: NumbersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.grabData("42,5")
        viewModel.grabData("7..10,5")
        setContent {
            NumbersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val minNumState = viewModel.minNumState.collectAsState()
                    val maxNumState = viewModel.maxNumState.collectAsState()
                    val textState = viewModel.textState.collectAsState()

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier,
                            text = "${minNumState.value}, ${maxNumState.value}",
                            style = TextStyle(fontSize = 30.sp)
                        )
                        InputTextField()
                    }
                }
            }
        }
    }

    @Composable
    fun InputTextField(
        modifier: Modifier = Modifier
    ) {
        val customTextSelectionColors = TextSelectionColors(
            handleColor = Transparent,
            backgroundColor = Transparent,
        )
        CompositionLocalProvider(
            LocalTextSelectionColors provides customTextSelectionColors,
            LocalTextInputService provides null
        ) {
            var text by rememberSaveable { mutableStateOf("     ") }

            BasicTextField(
                // modifier order matters!
                modifier = Modifier
                    .padding(16.dp)     // acts as margin
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFFEBE0F0))
                    .padding(12.dp),    // acts as padding
                value = text,
                onValueChange = {
                    text = it.filter { char ->
                        char != ' '
                    }.padEnd(5, ' ').substring(0, 5)
                },
                singleLine = true,
                cursorBrush = SolidColor(Transparent),
                textStyle = TextStyle(
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            )
        }
    }

    @Composable
    fun MainLayout(modifier: Modifier = Modifier) {

    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        val textState = viewModel.textState.collectAsState()
        val errorState = viewModel.errorState.collectAsState()

        Text(
            text = textState.value + "\n" + errorState.value,
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        NumbersTheme {
            Greeting("Android")
        }
    }
}