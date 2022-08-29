package vtsen.hashnode.dev.composesideeffectsdemo.ui.demo

import android.util.Log
import androidx.compose.runtime.*
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun DemoSnapShotFlow(){
    var textState =  remember { mutableStateOf("") }

    LaunchedEffect(textState) {
        // Convert State<T> to Flow<T>
        val flow = snapshotFlow { textState.value }
        // Ensure flow doesn't emit the same value twise
        flow.distinctUntilChanged()
        // Collect the flow
        flow.collect { text ->
            Log.d("[SnapShotFlow]", "collecting flow value: $text")
        }
    }
}