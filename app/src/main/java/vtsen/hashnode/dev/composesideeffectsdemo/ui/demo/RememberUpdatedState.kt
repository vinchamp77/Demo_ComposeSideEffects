package vtsen.hashnode.dev.composesideeffectsdemo.ui.demo

import android.util.Log
import androidx.compose.runtime.*
import kotlinx.coroutines.delay

@Composable
fun RememberUpdatedStated(value: String) {
    Log.d("RememberUpdatedStated", "RememberUpdatedStated() is called with $value ")
    val rememberValue = remember { value }
    val rememberMutableStateValue by remember { mutableStateOf(value) }
    val rememberUpdatedStateValue by rememberUpdatedState(value)

    LaunchedEffect(true) {
        while(true) {
            delay(1000)
            Log.d("RememberUpdatedStated",
                "[Old]: value: $value " +
                "[Old]:rememberValue: $rememberValue " +
                "[Old]:rememberMutableStateValue: $rememberMutableStateValue " +
                "[New]:rememberUpdatedStateValue: $rememberUpdatedStateValue "
            )
        }
    }
}
