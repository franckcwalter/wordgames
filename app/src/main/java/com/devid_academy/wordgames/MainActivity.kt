package com.devid_academy.wordgames

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.devid_academy.ui.GlobalMessageRepository
import com.devid_academy.wordgames.navigation.WordgamesNavigation
import com.devid_academy.wordgames.ui.theme.WordgamesTheme
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WordgamesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WordgamesNavigation(
                        innerPadding = innerPadding
                    )


                    val globalMessageRepository: GlobalMessageRepository = koinInject()
                    val context = LocalContext.current
                    LaunchedEffect(Unit) {
                        globalMessageRepository.observeErrorString().collect {
                            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                        }
                    }
                    LaunchedEffect(Unit) {
                        globalMessageRepository.observeErrorStringRes().collect {
                            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                        }
                    }

                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WordgamesTheme {
    }
}