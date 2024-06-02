//import android.graphics.Paint.Align
//import android.text.Layout
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.times
//import com.example.mimo.R
//
//@Preview(showBackground = true)
//@Composable
//fun LockScreenWithSeekBar() {
//    var progress by remember { mutableStateOf(50f) } // SeekBar의 기본값 설정 (0~100 범위)
//
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = Color.Black
//    ) {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Top,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Spacer(modifier = Modifier.height(32.dp))
//            Text(
//                text = "화면잠금중",
//                color = Color.White,
//                fontSize = 50.sp,
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Image(
//                modifier = Modifier
//                    .width(350.dp)
//                    .height(300.dp),
//                painter = painterResource(id = R.drawable.sleepmoon),
//                contentDescription = "잠금화면달"
//            )
//            Spacer(modifier = Modifier.height(40.dp))
//
//            // CustomSeekBar
//            CustomSeekBar(
//                progress = progress,
//                onProgressChange = { newValue ->
//                    progress = newValue
//                },
//                minValue = 0f,
//                maxValue = 100f,
//                thumbColor = Color.Red,
//                trackColors = listOf(Color.Blue, Color.Gray),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp)
//            )
//        }
//    }
//}
//
//@Composable
//fun CustomSeekBar(
//    progress: Float,
//    onProgressChange: (Float) -> Unit,
//    minValue: Float,
//    maxValue: Float,
//    thumbColor: Color,
//    trackColors: List<Color>,
//    modifier: Modifier = Modifier
//) {
//    val density = LocalDensity.current
//    Box(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(40.dp)
//            .background(
//                Brush.linearGradient(
//                    colors = trackColors,
//                    start = Alignment.TopStart,
//                    end = Alignment.TopEnd
//                ),
//                shape = RoundedCornerShape(4.dp)
//            )
//            .padding(16.dp)
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Box(
//                modifier = Modifier
//                    .size(20.dp)
//                    .background(thumbColor, shape = CircleShape)
//                    .offset( // Use offset with both x and y values
//                        x = (progress * (density.run { Modifier.fillMaxWidth().width.toDp() - 20.dp.toPx() })),
//                        y = 0.dp
//                    )
//            )
//
//            Text(
//                text = "${(progress * (maxValue - minValue) + minValue).toInt()}분",
//                color = Color.White,
//                fontWeight = FontWeight.Bold,
//                fontSize = 16.sp
//            )
//        }
//    }
//
//    // Handle dragging to update the progress
//    modifier.clickable { offset ->
//        val x = offset.x
//        val progressValue = (x / (density.run { Modifier.fillMaxWidth().width.toDp() - 20.dp.toPx() })) * (maxValue - minValue) + minValue
//        onProgressChange(progressValue)
//    }
//}
//
//
//
//
//
//@Preview(showBackground = true)
//@Composable
//fun LockScreenWithSeekBarPreview() {
//    LockScreenWithSeekBar()
//}
