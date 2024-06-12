package com.example.gorodki_diplom

// todo: these things:
/*
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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.example.gorodki_diplom.ui.theme.Gorodki_diplomTheme
import com.badlogic.gdx.Game

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.core.graphics.toRectF
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.*


import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.plus
import kotlin.math.max
import kotlin.math.min



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
            //Text("Classic game Screen", modifier = Modifier.padding(16.dp), color = Color.White)
            TestNCGameScreen()
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


//@Preview(widthDp = 400, heightDp = 800, showBackground = true)
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
            Box(modifier = Modifier.fillMaxSize()){
                // todo: game here

                //LibGDXGameContainer(modifier = Modifier.fillMaxSize())
                //MyGame()
                //GameScreen()

            }
        }
    }
}


@Composable
fun MenuScreenPreview() {
    Gorodki_diplomTheme {
        MenuScreen()
    }
}


fun doesLineIntersectRectangle(lineStart: Offset, lineEnd: Offset, rectTopLeft: Offset, rectSize: Size): Boolean {
    //val rectBottomRight = rectTopLeft + rectSize
    val rectBottomRight = Offset(x = rectTopLeft.x + rectSize.width, y = rectTopLeft.y + rectSize.height)


    // Check if the line is outside the rectangle's bounds
    if (lineStart.x < rectTopLeft.x && lineEnd.x < rectTopLeft.x) return false
    if (lineStart.x > rectBottomRight.x && lineEnd.x > rectBottomRight.x) return false
    if (lineStart.y < rectTopLeft.y && lineEnd.y < rectTopLeft.y) return false
    if (lineStart.y > rectBottomRight.y && lineEnd.y > rectBottomRight.y) return false

    // Check if the line intersects with any of the rectangle's edges
    val line = Line(lineStart, lineEnd)
    val top = Line(rectTopLeft, Offset(x = rectBottomRight.x, y = rectTopLeft.y))
    val bottom = Line(Offset(x = rectTopLeft.x, y = rectBottomRight.y), rectBottomRight)
    val left = Line(rectTopLeft, Offset(x = rectTopLeft.x, y = rectBottomRight.y))
    val right = Line(Offset(x = rectBottomRight.x, y = rectTopLeft.y), rectBottomRight)

    return line.intersects(top) || line.intersects(bottom) || line.intersects(left) || line.intersects(right)
}

data class Line(val start: Offset, val end: Offset) {
    fun intersects(other: Line): Boolean {
        val d1 = direction(start, end, other.start)
        val d2 = direction(start, end, other.end)
        val d3 = direction(other.start, other.end, start)
        val d4 = direction(other.start, other.end, end)

        if (d1 != d2 && d3 != d4) return true
        if (d1 == 0 && onSegment(start, other.start, end)) return true
        if (d2 == 0 && onSegment(start, other.end, end)) return true
        if (d3 == 0 && onSegment(other.start, start, other.end)) return true
        if (d4 == 0 && onSegment(other.start, end, other.end)) return true

        return false
    }

    private fun direction(p1: Offset, p2: Offset, p3: Offset): Int {
        val val1 = (p3.y - p1.y) * (p2.x - p1.x)
        val val2 = (p3.x - p1.x) * (p2.y - p1.y)
        return if (val1 < val2) -1 else if (val1 > val2) 1 else 0
    }

    private fun onSegment(p1: Offset, p2: Offset, p3: Offset): Boolean {
        return p2.x <= max(p1.x, p3.x) && p2.x >= min(p1.x, p3.x) &&
                p2.y <= max(p1.y, p3.y) && p2.y >= min(p1.y, p3.y)
    }
}

fun Offset.rotate(angle: Float): Offset {
    val radians = Math.toRadians(angle.toDouble())
    val cos = Math.cos(radians)
    val sin = Math.sin(radians)
    return Offset(x = (x * cos - y * sin).toFloat(), y = (x * sin + y * cos).toFloat())
}


@Preview(widthDp = 400, heightDp = 800, showBackground = true)
@Composable
fun TestNCGameScreen() {
    var angle by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    var isFirstRectVisible by remember { mutableStateOf(true) }
    var isSecondRectVisible by remember { mutableStateOf(true) }
    var isThirdRectVisible by remember { mutableStateOf(true) }
    var isFourthRectVisible by remember { mutableStateOf(true) }
    var isFifthRectVisible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        while (true) {
            angle = (angle + 2) % 360
            delay(8)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offset += dragAmount
                }
            }
    ) {
        Column {
            //Box(modifier = Modifier.fillMaxHeight(0.4f))
            Box(
                modifier = Modifier
                    //.background(Color.Green)
                    .fillMaxHeight()
                    .fillMaxWidth()
            ){
                Canvas(modifier = Modifier.fillMaxSize()){
                    val canvasWidth = size.width
                    val canvasHeight = size.height

                    // Throw line todo: move it according to the rules
                    translate(left = canvasWidth / 2, top = canvasHeight / 2) {
                        drawLine(
                            color = Color.White,
                            start = Offset(x = -canvasWidth, y = 0f),
                            end = Offset(x = canvasWidth, y = 0f),
                            strokeWidth = 4.0f
                        )
                    }

                    // Rectangles todo: make them levels
                    if (isFirstRectVisible) {
                        drawRect(
                            color = Color.White,
                            topLeft = Offset(x = canvasWidth / 3, y = canvasHeight / 6),
                            size = Size(width = canvasWidth / 60, height = canvasHeight / 40)
                        )
                    }
                    if (isSecondRectVisible) {
                        drawRect(
                            color = Color.White,
                            topLeft = Offset(x = canvasWidth / 6 + canvasWidth / 2, y = canvasHeight / 6),
                            size = Size(width = canvasWidth / 60, height = canvasHeight / 40)
                        )
                    }
                    if (isThirdRectVisible) {
                        drawRect(
                            color = Color.White,
                            topLeft = Offset(x = canvasWidth / 2, y = canvasHeight / 6),
                            size = Size(width = canvasWidth / 60, height = canvasHeight / 40)
                        )
                    }
                    if (isFourthRectVisible) {
                        drawRect(
                            color = Color.White,
                            topLeft = Offset(x = canvasWidth / 2 - (canvasWidth / 60), y = canvasHeight / 4),
                            size = Size(width = canvasHeight / 40, height = canvasWidth / 60)
                        )
                    }
                    if (isFifthRectVisible) {
                        drawRect(
                            color = Color.White,
                            topLeft = Offset(x = canvasWidth / 2 - (canvasWidth / 60), y = canvasHeight / 10),
                            size = Size(width = canvasHeight / 40, height = canvasWidth / 60)
                        )
                    }
                }

                // Bita line with checking if collided
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(
                            translationX = offset.x,
                            translationY = offset.y,
                            rotationZ = angle
                        )
                ) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height

                    // Rect positions todo: make it all into a class or something
                    val firstRectTopLeft = Offset(x = (canvasWidth / 3).toFloat(), y = (canvasHeight / 6).toFloat())
                    val firstRectSize = Size(width = (canvasWidth / 60).toFloat(), height = (canvasHeight / 40).toFloat())
                    val secondRectTopLeft = Offset(x = (canvasWidth / 6 + canvasWidth / 2).toFloat(), y = (canvasHeight / 6).toFloat())
                    val secondRectSize = Size(width = (canvasWidth / 60).toFloat(), height = (canvasHeight / 40).toFloat())
                    val thirdRectTopLeft = Offset(x = (canvasWidth / 2).toFloat(), y = (canvasHeight / 6).toFloat())
                    val thirdRectSize = Size(width = (canvasWidth / 60).toFloat(), height = (canvasHeight / 40).toFloat())
                    val fourthRectTopLeft = Offset(x = (canvasWidth / 2 + canvasWidth / 60).toFloat(), y = (canvasHeight / 4).toFloat())
                    val fourthRectSize = Size(width = (canvasWidth / 40).toFloat(), height = (canvasHeight / 60).toFloat())
                    val fifthRectTopLeft = Offset(x = (canvasWidth / 2 + canvasWidth / 60).toFloat(), y = (canvasHeight / 10).toFloat())
                    val fifthRectSize = Size(width = (canvasWidth / 40).toFloat(), height = (canvasHeight / 60).toFloat())


                    val lineStart = Offset(x = -canvasWidth / 6, y = 0f).rotate(angle) + Offset(x = canvasWidth / 2, y = canvasHeight / 2) + offset
                    val lineEnd = Offset(x = canvasWidth / 6, y = 0f).rotate(angle) + Offset(x = canvasWidth / 2, y = canvasHeight / 2) + offset

                    // Collision detection todo: delete until the end of level
                    isFirstRectVisible = !doesLineIntersectRectangle(lineStart, lineEnd, firstRectTopLeft, firstRectSize)
                    isSecondRectVisible = !doesLineIntersectRectangle(lineStart, lineEnd, secondRectTopLeft, secondRectSize)
                    isThirdRectVisible = !doesLineIntersectRectangle(lineStart, lineEnd, thirdRectTopLeft, thirdRectSize)
                    isFourthRectVisible = !doesLineIntersectRectangle(lineStart, lineEnd, fourthRectTopLeft, fourthRectSize)
                    isFifthRectVisible = !doesLineIntersectRectangle(lineStart, lineEnd, fifthRectTopLeft, fifthRectSize)

                    translate(left = canvasWidth / 2, top = canvasHeight / 2) {
                        drawLine(
                            color = Color.White,
                            start = Offset(x = -canvasWidth / 6, y = 0f),
                            end = Offset(x = canvasWidth / 6, y = 0f),
                            strokeWidth = 14.0f
                        )
                    }
                }
            }
        }
    }
}

/*
@Composable
fun TestNCGameScreen() {
    var angle by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    var isLeftRectangleVisible by remember { mutableStateOf(true) }
    var isRightRectangleVisible by remember { mutableStateOf(true) }


    LaunchedEffect(Unit) {
        while (true) {
            angle = (angle + 1) % 360
            delay(20)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)

    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            // Two static rectangles
            /*
            drawRect( // Left
                color = Color.White,
                topLeft = Offset(x = canvasWidth / 3, y = canvasHeight / 6),
                size = Size(width = canvasWidth / 60, height = canvasHeight / 40)
            )

            drawRect( // Right
                color = Color.White,
                topLeft = Offset(x = canvasWidth / 6 + canvasWidth / 2, y = canvasHeight / 6),
                size = Size(width = canvasWidth / 60, height = canvasHeight / 40)
            )
            */
            if (isLeftRectangleVisible) {
                drawRect(
                    color = Color.White,
                    topLeft = Offset(x = canvasWidth / 3, y = canvasHeight / 6),
                    size = Size(width = canvasWidth / 60, height = canvasHeight / 40)
                )
            }

            if (isRightRectangleVisible) {
                drawRect(
                    color = Color.White,
                    topLeft = Offset(x = canvasWidth / 6 + canvasWidth / 2, y = canvasHeight / 6),
                    size = Size(width = canvasWidth / 60, height = canvasHeight / 40)
                )
            }


        }

        Column {
            Box(modifier = Modifier.fillMaxHeight(0.4f))
            Box(
                modifier = Modifier
                    .background(Color.Green) //todo: hide this
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            offset += dragAmount
                            //todo: change to offset += Offset(x = dragAmount.x, y = 0f) after collision is working
                            //offset += Offset(x = dragAmount.x, y = 0f)
                        }
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            offset += dragAmount

                            // Calculate the endpoints of the line
                            //val lineStart = Offset(x = (size.width / 2 - size.width / 6).toFloat(), y = (size.height / 2).toFloat())
                            //val lineEnd = Offset(x = (size.width / 2 + size.width / 6).toFloat(), y = (size.height / 2).toFloat())

                            // Calculate the top left corner and size of each rectangle
                            val leftRectTopLeft = Offset(x = (size.width / 3).toFloat(), y = (size.height / 6).toFloat())
                            val leftRectSize = Size(width = (size.width / 60).toFloat(), height = (size.height / 40).toFloat())
                            val rightRectTopLeft = Offset(x = (size.width / 6 + size.width / 2).toFloat(), y = (size.height / 6).toFloat())
                            val rightRectSize = Size(width = (size.width / 60).toFloat(), height = (size.height / 40).toFloat())

                            // Calculate the transformed line's endpoints
                            val lineStart = Offset(x = (-size.width / 6).toFloat(), y = 0f).rotate(angle) + Offset(x = (size.width / 2).toFloat(), y = (size.height / 2).toFloat()) + offset
                            val lineEnd = Offset(x = (size.width / 6).toFloat(), y = 0f).rotate(angle) + Offset(x = (size.width / 2).toFloat(), y = (size.height / 2).toFloat()) + offset

                            // Check if the line intersects with each rectangle
                            isLeftRectangleVisible = !doesLineIntersectRectangle(lineStart, lineEnd, leftRectTopLeft, leftRectSize)
                            isRightRectangleVisible = !doesLineIntersectRectangle(lineStart, lineEnd, rightRectTopLeft, rightRectSize)

                            // Check if the line intersects with each rectangle
                            //isLeftRectangleVisible = !doesLineIntersectRectangle(lineStart, lineEnd, leftRectTopLeft, leftRectSize)
                            //isRightRectangleVisible = !doesLineIntersectRectangle(lineStart, lineEnd, rightRectTopLeft, rightRectSize)
                        }

                    }
            ){
                // The Throw line
                Canvas(modifier = Modifier.fillMaxSize()){
                    val canvasWidth = size.width
                    val canvasHeight = size.height

                    translate(left = canvasWidth / 2, top = canvasHeight / 2) {
                        drawLine(
                            color = Color.White,
                            start = Offset(x = -canvasWidth, y = 0f),
                            end = Offset(x = canvasWidth, y = 0f),
                            strokeWidth = 4.0f
                        )
                    }
                }

                // Transformations to the Bita line
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(
                            translationX = offset.x,
                            translationY = offset.y,
                            rotationZ = angle
                        )
                ) {
                    // The line here to be transformed
                    val canvasWidth = size.width
                    val canvasHeight = size.height

                    // Translate the line to its center before rotating it
                    translate(left = canvasWidth / 2, top = canvasHeight / 2) {
                        drawLine(
                            color = Color.White,
                            start = Offset(x = -canvasWidth / 6, y = 0f),
                            end = Offset(x = canvasWidth / 6, y = 0f),
                            strokeWidth = 14.0f
                        )
                    }
                }
            }
        }

    }
}
*/

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Gorodki_diplomTheme {
                //TestNCGameScreen()
                MenuScreen()
            }
        }
    }
}


