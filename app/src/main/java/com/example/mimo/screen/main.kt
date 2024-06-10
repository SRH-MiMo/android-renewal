package com.example.mimo.screen

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mimo.component.TopBar
import com.example.mimo.R
import androidx.compose.ui.text.TextStyle
import com.example.mimo.supabase
import com.example.mimo.ui.theme.PurpleEnd
import com.example.mimo.ui.theme.PurpleStart
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.IDToken

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainPage(navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar(name = "미라클 모닝 시작하기")
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .width(350.dp)
                    .height(300.dp),
                painter = painterResource(id = R.drawable.moon),
                contentDescription = "달사진"
            )

            Spacer(modifier = Modifier.height(40.dp))

            val context = LocalContext.current

            Box(
                modifier = Modifier
                    .width(400.dp)
                    .height(90.dp)
                    .padding(16.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(PurpleStart, PurpleEnd)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        val session = supabase.auth.currentSessionOrNull()

                        if (session?.user != null) {
                            Toast.makeText(
                                context,
                                "로그인 상태: ${session.user?.email}",
                                Toast.LENGTH_LONG
                            ).show()
                            navController.navigate("AlarmSettingScreen")
                        } else {
                            navController.navigate("LoginPage")
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "시작하기",
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.White, // Assuming white text color
                            fontWeight = FontWeight.Bold
                        )
                    )
                
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MainPreView() {
    // Create a fake NavController for preview purposes
    val navController = rememberNavController()
    MainPage(navController = navController)
}
