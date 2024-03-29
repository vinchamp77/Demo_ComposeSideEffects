package vtsen.hashnode.dev.composesideeffectsdemo.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import vtsen.hashnode.dev.composesideeffectsdemo.ui.common.tag
import vtsen.hashnode.dev.composesideeffectsdemo.ui.common.CommonScreen
import vtsen.hashnode.dev.composesideeffectsdemo.ui.demo.DemoScreen
import vtsen.hashnode.dev.composesideeffectsdemo.ui.theme.CoroutineScopeDemoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "Activity onDestroy() called")

        if(isFinishing) {
            Log.d(tag, "Activity is finishing!")
        }
    }
}

@Composable
fun MainScreen(useSystemUIController: Boolean = true) {
    CoroutineScopeDemoAppTheme(useSystemUIController = useSystemUIController) {

        CommonScreen {
            DemoScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen(useSystemUIController = false)
}