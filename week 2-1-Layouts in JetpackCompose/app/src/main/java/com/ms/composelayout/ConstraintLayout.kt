package com.ms.composelayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ms.composelayout.ui.theme.ComposeLayoutTheme

class ConstraintLayout : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLayoutTheme {
                ConstraintLayoutContent()
            }
        }
    }
}

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        val (button, text) = createRefs()

        Button(
            onClick = {},
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button")
        }

        Text("Text", modifier = Modifier.constrainAs(text) {
            top.linkTo(button.bottom, margin = 16.dp)
        })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    ComposeLayoutTheme {
        ConstraintLayoutContent()
    }
}