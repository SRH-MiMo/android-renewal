package com.example.mimo.screen


import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mimo.R
import com.example.mimo.supabase
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.providers.builtin.IDToken
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID

@Composable
fun Loginpage(navController: NavController) {

//    val session = supabase.auth.currentSessionOrNull()
//
//    println(session?.user?.id)

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "미모 로고",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
            )

            Text(
                text = "미모 - 미라클 모닝을 찾아서",
                color = Color.White,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Image(
                painter = painterResource(id = R.drawable.sky),
                contentDescription = "하늘사진",
                modifier = Modifier
                    .width(300.dp)
                    .height(300.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column {
//                InsertButton()
                GoogleSignInButton(navController = navController)

            }
        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    // Create a fake NavController for preview purposes
    val navController = rememberNavController()
    Loginpage(navController = navController)
}

@Composable
fun InsertButton() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Button(onClick = {
        coroutineScope.launch {
            try {
                supabase.from("posts").insert(mapOf("content" to "Hello from Android"))

                Toast.makeText(context, "New row inserted", Toast.LENGTH_SHORT).show()

            } catch (e: RestException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()

            }
        }
    }) {
        Text("insert a new row")
    }
}

@Composable
fun GoogleSignInButton(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val onClick: () -> Unit = {
        val credentialManager = CredentialManager.create(context)

        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }


        val googleIdOptions: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("602014460238-f4q1n85ocoo7u96ntgltuh9j48isoo5r.apps.googleusercontent.com")
            .setNonce(hashedNonce)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOptions)
            .build()

        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context,
                )
                val credential = result.credential

                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(credential.data)

                val googleIdToken = googleIdTokenCredential.idToken

                supabase.auth.signInWith(IDToken) {
                    idToken = googleIdToken
                    provider = Google
                    nonce = rawNonce
                }

                navController.navigate("MainHost")

                Toast.makeText(context, "you are signed in!", Toast.LENGTH_SHORT).show()
            } catch (e: GetCredentialException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                Log.d(TAG, "GoogleSignInButton: ${e.message}")

            } catch (e: GoogleIdTokenParsingException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }

        }
    }

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
    ) {
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "로그인 버튼"
        )
    }
}