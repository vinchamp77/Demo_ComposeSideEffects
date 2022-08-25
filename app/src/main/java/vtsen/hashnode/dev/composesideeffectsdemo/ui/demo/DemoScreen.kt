package vtsen.hashnode.dev.composesideeffectsdemo.ui.demo

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vtsen.hashnode.dev.composesideeffectsdemo.ui.common.LogCompositions
import vtsen.hashnode.dev.composesideeffectsdemo.ui.common.TextWidget
import vtsen.hashnode.dev.composesideeffectsdemo.ui.common.tag

@Composable
fun DemoScreen() {

    val text = remember { mutableStateOf("") }
    var startLaunchedEffect by remember { mutableStateOf(false)}
    var launchedEffectKey by remember { mutableStateOf(true)}
    var enableRememberUpdatedStated by remember { mutableStateOf(false)}


    val scope = rememberCoroutineScope()
    var job: Job? by remember { mutableStateOf(null)}

    LogCompositions(tag, "DemoScreen() function scope")

    if(startLaunchedEffect) {
        LaunchedEffect(true) {
            simulateSuspendFunction(text)
        }
    }

    if(enableRememberUpdatedStated) {
        RememberUpdatedStated(text.value)
    }

    Column {
        TextWidget(title = "[Text]", text = text.value , tag = tag)



        Button(onClick = {
            startLaunchedEffect = true
        }) {
            Text("Start Launched Effect")
        }

        Button(onClick = {
            startLaunchedEffect = false
        }) {
            Text("Stop Launched Effect")
        }

        Button(onClick = {
            launchedEffectKey = !launchedEffectKey
        }) {
            Text("Toggle Launched Effect Key")
        }

        Divider()

        /* Not Allowed in Composition
        scope.launch {
            simulateSuspendFunction(text)
        }*/

        Button(onClick = {
            job?.cancel()
            job = scope.launch {
                simulateSuspendFunction(text)
            }
        }) {
            Text("Start rememberCoroutineScope")
        }

        Button(onClick = {
            job?.cancel()
        }) {
            Text("Stop rememberCoroutineScope")
        }

        Divider()

        Button(onClick = { enableRememberUpdatedStated = true }
        ) {
            Text("Enable RememberUpdatedStated")
        }

        Button(onClick = { enableRememberUpdatedStated = false }
        ) {
            Text("Disable RememberUpdatedStated")
        }
    }
}

private suspend fun simulateSuspendFunction(text: MutableState<String>) {
    repeat(10000) { value ->
        delay(1000)
        Log.d(tag, "[LaunchedEffect] Set text to $value")
        text.value = value.toString()
    }
}

