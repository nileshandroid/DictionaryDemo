package com.pronesh.dictionarydemo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.pronesh.dictionarydemo.R
import com.pronesh.dictionarydemo.presentation.event.MainUIEvent
import com.pronesh.dictionarydemo.presentation.viewmodel.MainViewModel
import com.pronesh.dictionarydemo.ui.theme.DictionaryDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DictionaryDemoTheme {
                StatusBarColor()
                val mainViewModel = hiltViewModel<MainViewModel>()
                val mainState by mainViewModel.mainState.collectAsState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        OutlinedTextField(
                            value = mainState.searchWord,
                            onValueChange = {
                                mainViewModel.onEvent(
                                    MainUIEvent.OnSearchWord(it)
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Rounded.Search, contentDescription =
                                        getString(R.string.search_a_word),
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clickable {
                                            mainViewModel.onEvent((MainUIEvent.OnSearchClick))
                                        })
                            },
                            label = {
                                Text(
                                text = getString(R.string.search_a_word),
                                fontSize = 15.sp,
                                    modifier = Modifier.alpha(0.5f)
                                )
                            },
                            textStyle = TextStyle(
                                color = MaterialTheme.colorScheme.background,
                                fontSize = 19.sp,
                            )
                        )
                    }) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusBarColor() {
    val systemUiController = rememberSystemUiController()
    val color = MaterialTheme.colorScheme.background
    LaunchedEffect(color) {
        systemUiController.setSystemBarsColor(color)
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DictionaryDemoTheme {
        Greeting("Android")
    }
}