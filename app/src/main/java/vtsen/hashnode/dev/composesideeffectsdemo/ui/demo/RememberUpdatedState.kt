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

    // (2) LaunchedEffect is skipped during the second recomposition
    //	   when value is changed/updated
    LaunchedEffect(true) {
        while(true) {
            // (1) let's assume value is updated with a new value within
            //     1 second delay
            delay(1000)

            // (3) value is the initial value when LaunchedEffect is first called
            // (4) rememberMutableStateValue is the initial value from first composition
            // (5) rememberUpdatedStateValue is the new value from second recomposition
            Log.d("RememberUpdatedStated",
                "[Old]: value: $value " +
                "[Old]:rememberValue: $rememberValue " +
                "[Old]:rememberMutableStateValue: $rememberMutableStateValue " +
                "[New]:rememberUpdatedStateValue: $rememberUpdatedStateValue "
            )
        }
    }
}