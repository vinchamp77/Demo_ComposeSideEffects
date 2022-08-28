package vtsen.hashnode.dev.composesideeffectsdemo.ui.demo

import androidx.compose.runtime.*

@Composable
fun DemoDerivedStateOf() {
    var value1 by remember { mutableStateOf(true) }
    var value2 by remember { mutableStateOf(false) }

    val derivedValue by remember(value1) {
        derivedStateOf {
            "value1: $value1 + value2: $value2"
        }
    }
}
