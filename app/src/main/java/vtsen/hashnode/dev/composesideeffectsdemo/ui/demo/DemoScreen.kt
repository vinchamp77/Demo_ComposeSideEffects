package vtsen.hashnode.dev.composesideeffectsdemo.ui.demo

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import vtsen.hashnode.dev.composesideeffectsdemo.ui.common.LogCompositions
import vtsen.hashnode.dev.composesideeffectsdemo.ui.common.TextWidget
import vtsen.hashnode.dev.composesideeffectsdemo.ui.common.tag

@Composable
fun DemoScreen() {

    val textState = remember { mutableStateOf("") }

    LogCompositions(tag, "DemoScreen() function scope")

    var startLaunchedEffect by remember { mutableStateOf(false)}
    var launchedEffectKey by remember { mutableStateOf(true)}
    if(startLaunchedEffect) {
        LaunchedEffect(launchedEffectKey) {
            simulateSuspendFunction(textState)
        }
    }

    var enableRememberUpdatedStated by remember { mutableStateOf(false)}
    if(enableRememberUpdatedStated) {
        RememberUpdatedStated(textState.value)
    }

    val scope = rememberCoroutineScope()
    var job: Job? by remember { mutableStateOf(null)}

    var startDisposableEffect by remember { mutableStateOf(false)}
    var disposableEffectKey by remember { mutableStateOf(true)}
    if(startDisposableEffect) {
        DisposableEffect(disposableEffectKey) {

            repeat(10 ) { value ->
                Thread.sleep(1000)
                Log.d("DisposableEffect", "value: $value")
            }
            textState.value = "DisposableEffect() is called"
            Log.d("DisposableEffect", "DisposableEffect is called")

            onDispose {
                textState.value = "onDisposed() is called"
                Log.d("DisposableEffect", "onDisposed is called")
            }
        }
    }

    var startSideEffect by remember { mutableStateOf(false)}
    if(startSideEffect) {
        SideEffect {
            textState.value = "SideEffect() is called"
            Log.d("SideEffect", "SideEffect is called")
        }
    }

    var startProduceState by remember { mutableStateOf(false)}
    var textProduceState:State<String>? = null
    if(startProduceState) {
        textProduceState = produceState(initialValue = "") {

            val produceStateJob = MainScope().launch {
                repeat(10) { count ->
                    delay(1000)
                    Log.d("produceState", "Set value to $count")
                    value = count.toString()
                }
            }

            awaitDispose {
                Log.d("produceState", "awaitDispose() is called")
                produceStateJob.cancel()
            }
        }
    }

    var startDerivedStateOf by remember { mutableStateOf(false)}
    var value1 by remember { mutableStateOf(true)}
    var value2 by remember { mutableStateOf(false)}
    var derivedValue:State<String>? = null
    if(startDerivedStateOf) {
        derivedValue = remember(value1) {
            derivedStateOf {
                "value!: $value1 + $value2"
            }
        }
    }

    var startSnapShotFlow by remember { mutableStateOf(false)}
    if(startSnapShotFlow) {
        LaunchedEffect(textState) {
            val flow = snapshotFlow { textState.value }
            flow.distinctUntilChanged()
            flow.collect { text ->
                Log.d("[SnapShotFlow]", "collecting flow text: $text")
            }
        }
    }

    Column {
        TextWidget(title = "[TextState]", text = textState.value , tag = tag)
        if(textProduceState != null) {
            TextWidget(title = "[TextProduceState]", text = textProduceState.value, tag = tag)
        }
        if(derivedValue != null) {
            TextWidget(title = "[DerivedValue]", text = derivedValue.value, tag = tag)
        }

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

        Button(onClick = { enableRememberUpdatedStated = true }
        ) {
            Text("Enable RememberUpdatedStated")
        }

        Button(onClick = { enableRememberUpdatedStated = false }
        ) {
            Text("Disable RememberUpdatedStated")
        }

        Divider()

        /* Not Allowed in Composition
        scope.launch {
            simulateSuspendFunction(text)
        }*/


        Button(onClick = {
            job = scope.launch {
                simulateSuspendFunction(textState)
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

        Button(onClick = {
            startDisposableEffect = true
        }) {
            Text("Start Disposable Effect")
        }

        Button(onClick = {
            startDisposableEffect = false
        }) {
            Text("Stop Disposable Effect")
        }

        Button(onClick = {
            disposableEffectKey = !disposableEffectKey
        }) {
            Text("Toggle Disposable Effect Key")
        }

        Divider()

        Button(onClick = {
            startSideEffect = true
        }) {
            Text("Start Side Effect")
        }

        Button(onClick = {
            startSideEffect = false
        }) {
            Text("Stop Side Effect")
        }

        Divider()

        Button(onClick = {
            startProduceState = true
        }) {
            Text("Start Produce State")
        }

        Button(onClick = {
            startProduceState = false
        }) {
            Text("Stop Produce State")
        }

        Divider()

        Button(onClick = {
            startDerivedStateOf = true
        }) {
            Text("Start Derived State")
        }

        Button(onClick = {
            startDerivedStateOf = false
        }) {
            Text("Stop Derived State")
        }
        Button(onClick = {
            value1 = !value1
        }) {
            Text("Toggle value1")
        }

        Button(onClick = {
            value2 = !value2
        }) {
            Text("Toggle value2")
        }

        Divider()

        Button(onClick = {
            startSnapShotFlow = true
        }) {
            Text("Start Snap Shot Flow")
        }

        Button(onClick = {
            startSnapShotFlow = false
        }) {
            Text("Stop Snap Shot Flow")
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

