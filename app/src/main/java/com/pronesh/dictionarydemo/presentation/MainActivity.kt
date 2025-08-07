package com.pronesh.dictionarydemo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.pronesh.dictionarydemo.R
import com.pronesh.dictionarydemo.domain.model.Meaning
import com.pronesh.dictionarydemo.domain.model.WordItem
import com.pronesh.dictionarydemo.presentation.event.MainUIEvent
import com.pronesh.dictionarydemo.presentation.state.MainState
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
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 40.dp),
                    topBar = {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
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
                                )
                            },
                            textStyle = TextStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 19.sp,
                            )
                        )
                    }) { innerPadding ->
                    val padding = innerPadding
                    ShowMainLayout(mainState, innerPadding)
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
private fun ShowMainLayout(mainState: MainState, innerPadding: PaddingValues) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(top = innerPadding.calculateTopPadding())
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            Text(
                text = mainState.wordItem?.word.toString(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 10.dp)

            )
            Text(
                text = mainState.wordItem?.phonetic.toString(),
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .alpha(0.5f),
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .padding(top = 100.dp)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(MaterialTheme.colorScheme.primary.copy(0.5f))

        ) {
            if (mainState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(60.dp)
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                mainState.wordItem?.let { wordItem ->
                    WordContent(wordItem)
                }
            }

        }
    }
}

@Composable
fun WordContent(words: WordItem) {
    LazyColumn(
        contentPadding = PaddingValues(
            horizontal = 10.dp
        )
    ) {
        items(words.meanings.size) { index ->
            Spacer(modifier = Modifier.height(30.dp))
            MeaningDetails(
                meaning = words.meanings[index],
                index
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun MeaningDetails(meaning: Meaning, index: Int) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "${index + 1}. ${meaning.partOfSpeech}",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primary.copy(0.7f),
                            Color.Transparent
                        )
                    )
                )
                .padding(top = 2.dp, bottom = 4.dp, start = 10.dp, end = 10.dp)
        )

        DetailsDefinitions(meaning.definitions.definition, stringResource(R.string.definiation))
        DetailsDefinitions(meaning.definitions.example, stringResource(R.string.example))
    }
}

@Composable
private fun DetailsDefinitions(value: String, key: String) {
    if (value.isNotEmpty()) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.padding(horizontal = 10.dp)) {
            Text(
                text = key,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}