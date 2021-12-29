package com.ms.composelayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ms.composelayout.ui.theme.ComposeLayoutTheme

class Intrinsics : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLayoutTheme {
                Surface{
                    TwoTexts(text1 = "Hi", text2 = "there")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    ComposeLayoutTheme {
        Surface{
            TwoTexts(text1 = "Hi", text2 = "there")
        }
    }
}

/**
 * 컴포즈의 children는 단 한번만 measure 한다.
 * 그러나 가끔 children의 정보가 필요할 때가 있다.
 * 이때 Intrinsics를 사용한다.
 */
@Composable
fun TwoTexts(modifier: Modifier = Modifier, text1: String, text2: String) {
    // Modifier.height(IntrinsicSize.Min)을 사용하면
    // Row의 children 모두 IntrinsicSize.Min으로 강제한다.
    Row(modifier = modifier.height(IntrinsicSize.Min)) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentWidth(Alignment.Start),
            text = text1
        )

        Divider(color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .wrapContentWidth(Alignment.End),
            text = text2
        )
    }
}

