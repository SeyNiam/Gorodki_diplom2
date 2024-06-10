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

// Colours
var darkCyan = 0xFF008080
var darkerCyan = 0xFF004040

// Reusable Title text
@Composable
fun PrintTileText(text: String, modifier: Modifier = Modifier) {
    Text(
            text = "$text",
            modifier = modifier,
            fontSize = 44.sp,
            fontWeight = FontWeight.Light,
            color = Color(darkerCyan)
    )
}

// Button modifiers
fun Modifier.circleBtn() = this.then(
    Modifier
        .clip(CircleShape)
)
fun Modifier.menuBtn() = this.then(
    Modifier
        .fillMaxWidth(fraction = 0.9f)
        .height(64.dp)
)


// Reusable Button Composables
@Composable
fun CustomMenuButton(
    text: String,
    onClick: () -> Unit,
    icon: Int? = null, // Optional image resource
    modifier: Modifier = Modifier,
    textColor: Color = Color.White, // Default text color
    backgroundColor: Color = Color(darkCyan)
) {
    Button(
        onClick = onClick,
        modifier = modifier.menuBtn(),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        )
    ) {
        if (icon != null) {
            // Display image if provided
            Image(
                painter = painterResource(id = icon),
                contentDescription = "Button Icon",
                modifier = Modifier.size(24.dp)
            )
        } else {
            // Display text if no image provided
            Text(
                text = text,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = textColor // Use the provided text color
            )
        }
    }
}

@Composable
fun CustomCircleButton(
    text: String,
    onClick: () -> Unit,
    icon: Int? = null, // Optional image resource
    modifier: Modifier = Modifier,
    textColor: Color = Color.White, // Default text color
    backgroundColor: Color = Color(darkCyan)
) {
    Button(
        onClick = onClick,
        modifier = modifier.circleBtn(),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        )
    ) {
        if (icon != null) {
            // Display image if provided
            Image(
                painter = painterResource(id = icon),
                contentDescription = "Button Icon",
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
            )
        } else {
            // Display text if no image provided
            Text(
                text = text,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = textColor // Use the provided text color
            )
        }
    }
}


// All screens
enum class Screen {
    Menu, Profile, Settings, CGame, Rules
}

@Composable
fun MenuScreen() {
    var currentScreen by remember { mutableStateOf(Screen.Menu) }
    when (currentScreen) {
        Screen.Menu -> HomeScreen(
            onProfileClick = { currentScreen = Screen.Profile },
            onSettingsClick = { currentScreen = Screen.Settings },
            onCGameClick = { currentScreen = Screen.CGame },
            onRulesClick = { currentScreen = Screen.Rules }
        )
        Screen.Profile -> ProfileScreen { currentScreen = Screen.Menu }
        Screen.Settings -> SettingsScreen { currentScreen = Screen.Menu }
        Screen.CGame -> CGameScreen { currentScreen = Screen.Menu }
        Screen.Rules -> RulesScreen { currentScreen = Screen.Menu }
    }
}

@Composable
fun HomeScreen(onProfileClick: () -> Unit, onSettingsClick: () -> Unit, onCGameClick: () -> Unit, onRulesClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        ) {
            Box(modifier = Modifier.fillMaxHeight(fraction = 0.04f))
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp, 10.dp, 0.dp),
            ){
                CustomCircleButton(text = "Profile Icon", onClick = onProfileClick, R.drawable.pfp)
                CustomCircleButton(text = "Settings Icon", onClick = onSettingsClick, R.drawable.sttng)
            }

            Box(modifier = Modifier.fillMaxHeight(fraction = 0.02f))
            PrintTileText(text = "ГОРОДКИ")

            Box(modifier = Modifier.fillMaxHeight(fraction = 0.08f))
            CustomMenuButton(text = "Классическая игра", onClick = onCGameClick)

            Box(modifier = Modifier.fillMaxHeight(fraction = 0.04f))
            CustomMenuButton(text = "Правила", onClick = onRulesClick)

        }
    }
}



@Composable
fun ProfileScreen(onScreenChange: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        ) {
            Box(modifier = Modifier.fillMaxHeight(fraction = 0.04f))
            Row (
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp, 10.dp, 0.dp),
            ){
                CustomCircleButton(text = "<", onClick = onScreenChange)
            }
            Text("Profile Screen", modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun SettingsScreen(onScreenChange: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        ) {
            Box(modifier = Modifier.fillMaxHeight(fraction = 0.04f))
            Row (
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp, 10.dp, 0.dp),
            ){
                CustomCircleButton(text = "<", onClick = onScreenChange)
            }
            Text("Settings Screen", modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun CGameScreen(onScreenChange: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
        ) {
            Box(modifier = Modifier.fillMaxHeight(fraction = 0.04f))
            Row (
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp, 10.dp, 0.dp),
            ){
                CustomCircleButton(text = "<", onClick = onScreenChange)
            }
            Text("Classic game Screen", modifier = Modifier.padding(16.dp), color = Color.White)
        }
    }
} // todo: move layout of preview TestCGameScreen here

@Composable
fun RulesScreen(onScreenChange: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        ) {
            Box(modifier = Modifier.fillMaxHeight(fraction = 0.04f))
            Row (
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp, 10.dp, 0.dp),
            ){
                CustomCircleButton(text = "<", onClick = onScreenChange)
            }
            Text("Rules Screen", modifier = Modifier.padding(16.dp))
        }
    }
}


@Preview(widthDp = 400, heightDp = 800, showBackground = true)
@Composable
fun TestCGameScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
        ) {
            Box(modifier = Modifier.fillMaxHeight(fraction = 0.04f))
            Text("Classic game Screen", modifier = Modifier.padding(16.dp), color = Color.White)
            Box(modifier = Modifier.fillMaxSize().background(Color.DarkGray)){

            }
        }
    }
}


@Preview(widthDp = 400, heightDp = 800, showBackground = true)
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
