package vtsen.hashnode.dev.composesideeffectsdemo.ui.demo

import androidx.compose.runtime.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DemoProduceState(){
    val text by produceState(initialValue ="") {

        repeat(10) { count ->
            delay(1000)
            value = count.toString()
        }
    }
}


@Composable
fun DemoLaunchedEffect(){
    var text by remember { mutableStateOf("")}

    LaunchedEffect(true) {
        repeat(10) { count ->
            delay(1000)
            text = count.toString()
        }
    }
}

@Composable
fun DemoProduceStateAwaitDispose(){
    val text by produceState(initialValue ="") {

        val job = MainScope().launch {
            repeat(10) { count ->
                delay(1000)
                value = count.toString()
            }
        }

        awaitDispose {
            job.cancel()
        }
    }
}