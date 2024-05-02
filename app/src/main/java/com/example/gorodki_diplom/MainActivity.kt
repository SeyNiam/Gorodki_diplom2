package com.example.gorodki_diplom

/* todo
- change icon (res/drawable and/or mipmap?)
res/values:
    - add cool colours with names (no lame numbers)
    - add text through strings
    - figure out what themes are
- figure out all in res/xml folder
- figure out all in ui.theme folder (and res/values relation. contradictions?)
- there was a game option in c++, but for later android versions. right choice?
- i can add c++ tp module (file). look into it
- fun stuff in tools

- menu
- game itself (at least 1 lvl)
- option to stop & quit & stuff
- login, reg
- account/profile page
- leaderboard
- all af main game
- settings (sound & smth else)
- multiplayer if have time
- themes? (bg, colours, etc)
*/

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gorodki_diplom.ui.theme.Gorodki_diplomTheme

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


/*
sealed class Screen(val route: String) {
    object Menu : Screen("Menu")
    object ClassicGame : Screen("ClassicGame")
    object EndlessGame : Screen("EndlessGame")
    object Leaderboard : Screen("Leaderboard")
    object Rules : Screen("Rules")
    object Profile : Screen("Profile")
    object Settings : Screen("Settings")
}
*/


@Composable
fun PrintText(text: String, modifier: Modifier = Modifier) {
    Text(
            text = "$text",
            modifier = modifier
    )
}

fun Modifier.circleBtn() = this.then(
    Modifier
        //.size(52.dp)
        .clip(CircleShape)
        //.background(Color.Gray)
)
fun Modifier.menuBtn() = this.then(
    Modifier
        .fillMaxWidth(fraction = 0.9f)
        .height(52.dp)
        //.clip(shape = RoundedCornerShape(6.dp))
        //.background(Color.Gray)
)


// todo: copy this layout thingy into normal menu
@Composable
fun ColumnSample(/*navController: NavController*/) {
    /*
    if (navController != null) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        ) {
            Box(modifier = Modifier.fillMaxHeight(fraction = 0.04f))

            // todo: circle settings & pfp pic here and stuff
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp, 10.dp, 0.dp),
            ){
                Box(modifier = Modifier.grayCircle())
                Box(modifier = Modifier.grayCircle())
            }

            // todo: cool game title here ig
            PrintText("ГОРОДКИ")
            Spacer(modifier = Modifier.height(12.dp))

            // also how tf do i make those buttons anyone?? help??? text?? likns?? actionfs??
            // todo: figure out how to make those buttons
            Box(modifier = Modifier
                .menuBox()
                .clickable {
                    navController.navigate(Screen.ScreenMenu.route)
                }
            )
            Spacer(modifier = Modifier.height(6.dp))
            Box(modifier = Modifier.menuBox())
            Spacer(modifier = Modifier.height(6.dp))
            Box(modifier = Modifier.menuBox())
            Spacer(modifier = Modifier.height(6.dp))
            Box(modifier = Modifier.menuBox())
            Spacer(modifier = Modifier.height(6.dp))
        }
    } else {
        // Handle the case when navController is null, for example:
        Text("Error: NavController is null")
    }
    */
    Text("test without NavController")
}

enum class Screen {
    Menu, Profile, Settings
}

@Composable
fun MenuScreen() {
    var currentScreen by remember { mutableStateOf(Screen.Menu) }
    when (currentScreen) {
        Screen.Menu -> HomeScreen(
            onProfileClick = { currentScreen = Screen.Profile },
            onSettingsClick = { currentScreen = Screen.Settings }
        )
        Screen.Profile -> ProfileScreen { currentScreen = Screen.Menu }
        Screen.Settings -> SettingsScreen { currentScreen = Screen.Menu }
    }
}


@Composable
fun HomeScreen(onProfileClick: () -> Unit, onSettingsClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onProfileClick,
                modifier = Modifier.menuBtn(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF008080) // Dark Cyan
                )//,shape = ButtonDefaults.Shape.RoundedCornerShape(16.dp) // Rounded Corners
            ) {
                Text("Profile", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onSettingsClick,
                modifier = Modifier.circleBtn(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF008080) // Dark Cyan
                )
            ) {
                //Text("Settings")
                // Image on the button
                Image(
                    painter = painterResource(id = R.drawable.sttng), // Replace with your actual image resource
                    contentDescription = "Settings Icon",
                    modifier = Modifier.size(24.dp) // Adjust size as needed
                )
            }
        }
    }
}



@Composable
fun ProfileScreen(onScreenChange: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Profile Screen", modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onScreenChange,
            modifier = Modifier.padding(16.dp)
        ) {
            Text("PBack to Menu")
        }
    }
}

@Composable
fun SettingsScreen(onScreenChange: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Settings Screen", modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onScreenChange,
            modifier = Modifier.padding(16.dp)
        ) {
            Text("SBack to Menu")
        }
    }
}




@Preview(widthDp = 300, heightDp = 700, showBackground = true)
@Composable
fun MenuScreenPreview() {
    Gorodki_diplomTheme {
        MenuScreen()
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Gorodki_diplomTheme {
                MenuScreen()
            }
        }
    }
}
