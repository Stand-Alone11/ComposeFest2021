package com.ms.composelayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.atLeast
import com.ms.composelayout.ui.theme.ComposeLayoutTheme

class ConstraintLayout : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLayoutTheme {
//                ConstraintLayoutContent()
//                LargeConstraintLayout()
                DecoupledConstraintLayout()
            }
        }
    }
}

@Composable
fun DecoupledConstraintLayout() {
    BoxWithConstraints {
        // 디바이스 방향에 따라 maxWidth, maxHeight가 바뀌면서 재 렌더링 된다.
        // 이때, constraints의 값이 바뀌면서 각 레이아웃 id로 컴포저블을 찾아
        // constraint를 적용한다.
        val constraints = if (maxWidth < maxHeight) {
            decoupledConstraints(margin = 16.dp)
        } else {
            decoupledConstraints(margin = 32.dp)
        }

        ConstraintLayout(constraintSet = constraints) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.layoutId("button")
            ) {
                Text("Button")
            }

            Text("Text", Modifier.layoutId("text"))
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button) {
            top.linkTo(parent.top, margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin)
        }
    }
}

@Composable
fun LargeConstraintLayout() {
    ConstraintLayout {
        val text = createRef()

        //constraintlayout의 중앙 가이드라인
        val guideline = createGuidelineFromStart(fraction = .5f)
        //예상과는 다르게 parent의 중앙에서 시작하지 않는다.
        Text("This is a very very very very very very very long text",
            Modifier.constrainAs(text) {
                linkTo(start = guideline, end = parent.end)
                //preferredWrapContent는 constraint에 맞게 wrapcontent를 한다.
                //또한 최소 크기 강제도 가능하다.
                width = Dimension.preferredWrapContent.atLeast(500.dp)
            }
        )
    }
}

/**
 * constraintlayout의 자식들은 모두 레퍼런스로 만들어야 한다.
 * 각 컴포저블 모디파이어의 constrainAs()로 레퍼런스 매개변수와 연결한다.
 * linkTo() 메서드로 컴포저블 포지션을 정한다.
 */
@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        // 자식 컴포넌트들을 모두 엮어 레퍼런스를 만들어야 한다.
        val (button1, button2, text) = createRefs()

        Button(
            onClick = {},
            // 레퍼런스를 만든 매개 변수를 .constrainAs()로 매핑한다.
            modifier = Modifier.constrainAs(button1) {
                //lintTo()로 포지셔닝을 한다.
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button1")
        }

        Text("Text", modifier = Modifier.constrainAs(text) {
            top.linkTo(button1.bottom, margin = 16.dp)
            centerAround(button1.end)
        })

        // end barrier는 엮인 컴포넌트들 중에 가장 오른쪽 끝을 기준으로 한다.
        // rtl 이면 왼쪽 끝이 되겠다.
        val barrier = createEndBarrier(text, button1)
        Button(
            onClick = {},
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(barrier)
            }
        ) {
            Text("button2")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    ComposeLayoutTheme {
//        ConstraintLayoutContent()
//        LargeConstraintLayout()
        DecoupledConstraintLayout()
    }
}