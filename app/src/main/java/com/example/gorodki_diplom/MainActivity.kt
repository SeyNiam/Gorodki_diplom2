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
import androidx.compose.ui.graphics.drawscope.translate

/*
@Composable
fun rememberedGameState(): Pair<Line, List<Square>> {
    val line = remember { Line(x = 0f, y = 0f) }
    val squares = remember {
        mutableStateListOf<Square>().apply {
            repeat(10) {
                add(Square(x = Random.nextFloat() * 1000, y = Random.nextFloat() * 2000))
            }
        }
    }
    return Pair(line, squares)
}

data class Line(
    var x: Float,
    var y: Float,
    var angle: Float = 0f, // Initialize the angle to 0
    val length: Float = 100f,
    val thickness: Float = 5f
)


data class Square(
    var x: Float,
    var y: Float,
    val size: Float = 20f
)
*/


//@Preview(widthDp = 400, heightDp = 800, showBackground = true)
/*
@Composable
fun GameScreen(
    line: Line,
    squares: List<Square>,
    onLinePress: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawLine(
                color = Color.White,
                start = Offset(line.x - line.thickness / 2, line.y - line.thickness / 2),
                end = Offset(
                    line.x + line.length * cos(line.angle) - line.thickness / 2,
                    line.y + line.length * sin(line.angle) - line.thickness / 2
                ),
                strokeWidth = line.thickness
            )

            squares.forEach { square ->
                drawRect(
                    color = Color.White,
                    topLeft = Offset(square.x, square.y),
                    size = Size(square.size, square.size)
                )
            }
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(line.x.toInt(), line.y.toInt()) }
                .size(line.thickness.dp, line.thickness.dp)
                .clickable(onClick = onLinePress)
        )
    }
}
*/

/*
@Composable
fun GameScreen(
    line: Line,
    squares: List<Square>,
    onLinePress: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawLine(
                color = Color.White,
                start = Offset(line.x - line.thickness / 2, line.y - line.thickness / 2),
                end = Offset(
                    line.x + line.length * cos(line.angle) - line.thickness / 2,
                    line.y + line.length * sin(line.angle) - line.thickness / 2
                ),
                strokeWidth = line.thickness
            )

            squares.forEach { square ->
                drawRect(
                    color = Color.White,
                    topLeft = Offset(square.x, square.y),
                    size = Size(square.size, square.size)
                )
            }
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(line.x.toInt(), line.y.toInt()) }
                .size(line.thickness.dp, line.thickness.dp)
                .clickable(onClick = onLinePress)
        )
    }
}
*/










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
            Box(modifier = Modifier.fillMaxSize()){
                // todo: game here

                //LibGDXGameContainer(modifier = Modifier.fillMaxSize())
                //MyGame()
                //GameScreen()

            }
        }
    }
}

/*
@Composable
fun LibGDXGameContainer(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    AndroidView(
        modifier = modifier,
        factory = { context ->
            val config = AndroidApplicationConfiguration()
            object : AndroidFragmentApplication() {
                fun createApplication(): MyLibGDXGame {
                    return MyLibGDXGame() // Your LibGDX game class
                }

                override fun startActivity(intent: Intent?) {
                    TODO("Not yet implemented")
                }
            }
        }
    )
}
*/

//@Preview(widthDp = 400, heightDp = 800, showBackground = true)
@Composable
fun MenuScreenPreview() {
    Gorodki_diplomTheme {
        MenuScreen()
    }
}

/*
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
*/
/*
class MainActivity : ComponentActivity() {
    private val line = Line(x = 0f, y = 0f)
    // Use mutableStateListOf instead of listOf and make it a var to be able to modify it
    private var squares = mutableStateListOf<Square>()
        .apply {
            repeat(10) {
                add(Square(x = Random.nextFloat() * 1000, y = Random.nextFloat() * 2000))
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Gorodki_diplomTheme {
                // Wrap GameScreen in a remember block to store the state of line and squares
                remember {
                    GameScreen(
                        line = line,
                        squares = squares,
                        onLinePress = {
                            line.angle += 90f
                            line.y -= 10f

                            // Collision detection
                            squares.forEachIndexed { index, square ->
                                if (isCollision(line, square)) {
                                    // Use removeAt(index) on the mutableStateListOf
                                    squares.removeAt(index)
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    private fun isCollision(line: Line, square: Square): Boolean {
        val lineStart = Offset(line.x, line.y)
        val lineEnd = Offset(
            line.x + line.length * cos(line.angle),
            line.y +
                    line.length * sin(line.angle)
        )


// Calculate the bounding box of the square
        val squareLeft = square.x
        val squareTop = square.y
        val squareRight = square.x + square.size
        val squareBottom = square.y + square.size

// Check if the line's start or end point is inside the square's bounding box
        if (lineStart.x in squareLeft..squareRight && lineStart.y in squareTop..squareBottom) {
            return true
        }
        if (lineEnd.x in squareLeft..squareRight && lineEnd.y in squareTop..squareBottom) {
            return true
        }

// If not, check for intersection between the line and the square's edges
        val edges = listOf(
            Pair(Offset(squareLeft, squareTop), Offset(squareRight, squareTop)),
            Pair(Offset(squareRight, squareTop), Offset(squareRight, squareBottom)),
            Pair(Offset(squareRight, squareBottom), Offset(squareLeft, squareBottom)),
            Pair(Offset(squareLeft, squareBottom), Offset(squareLeft, squareTop))
        )

        edges.forEach { (edgeStart, edgeEnd) ->
            if (intersects(lineStart, lineEnd, edgeStart, edgeEnd)) {
                return true
            }
        }

        return false
    }

    private fun intersects(
        start1: Offset,
        end1: Offset,
        start2: Offset,
        end2: Offset
    ): Boolean {
        val det = (end2.x - start2.x) * (end1.y - start1.y) - (end1.x - start1.x) * (end2.y - start2.y)
        if (det == 0f) {
            return false
        }

        val t = ((start2.y - start1.y) * (start1.x - start2.x) + (start1.x - start2.x) * (start1.y - start2.y)) / det
        val u = -((start2.y - start1.y) * (start1.x - start2.x) + (start1.x - start2.x) * (start1.y - start2.y)) / det

        return t in 0f..1f && u in 0f..1f
    }
}
*/
/*
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Gorodki_diplomTheme {
                // Use the rememberedGameState composable function to store the game state
                val (line, squares) by rememberedGameState()

                GameScreen(
                    line = line,
                    squares = squares,
                    onLinePress = {
                        line.angle += 90f
                        line.y -= 10f

                        // Collision detection
                        squares.forEachIndexed { index, square ->
                            if (isCollision(line, square)) {
                                // Use removeAt(index) on the mutableStateListOf
                                squares.removeAt(index)
                            }
                        }
                    }
                )
            }
        }
    }

    private fun isCollision(line: Line, square: Square): Boolean {
        val lineStart = Offset(line.x, line.y)
        val lineEnd = Offset(
            line.x + line.length * cos(line.angle),
            line.y + line.length * sin(line.angle)
        )

        // Calculate the bounding box of the square
        val squareLeft = square.x
        val squareTop = square.y
        val squareRight = square.x + square.size
        val squareBottom = square.y + square.size

        // Check if the line's start or end point is inside the square's bounding box
        if (lineStart.x in squareLeft..squareRight && lineStart.y in squareTop..squareBottom) {
            return true
        }
        if (lineEnd.x in squareLeft..squareRight && lineEnd.y in squareTop..squareBottom) {
            return true
        }

        // If not, check for intersection between the line and the square's edges
        val edges = listOf(
            Pair(Offset(squareLeft, squareTop), Offset(squareRight, squareTop)),
            Pair(Offset(squareRight, squareTop), Offset(squareRight, squareBottom)),
            Pair(Offset(squareRight, squareBottom), Offset(squareLeft, squareBottom)),
            Pair(Offset(squareLeft, squareBottom), Offset(squareLeft, squareTop))
        )
        )
        edges.forEach { (edgeStart, edgeEnd) ->
            if (intersects(lineStart, lineEnd, edgeStart, edgeEnd)) {
                return true
            }
        }

        return false
    }

    private fun intersects(
        start1: Offset,
        end1: Offset,
        start2: Offset,
        end2: Offset
    ): Boolean {
        val det = (end2.x - start2.x) * (end1.y - start1.y) - (end1.x - start1.x) * (end2.y - start2.y)
        if (det == 0f) {
            return false
        }

        val t = ((start2.y - start1.y) * (start1.x - start2.x) + (start1.x - start2.x) * (start1.y - start2.y)) / det
        val u = -((start2.y - start1.y) * (start1.x - start2.x) + (start1.x - start2.x) * (start1.y - start2.y)) / det

        return t in 0f..1f && u in 0f..1f
    }
}
*/


@Preview(widthDp = 400, heightDp = 800, showBackground = true)
/**/
@Composable
fun TestNCGameScreen() {
    var angle by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

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

        }

        Column {
            Box(modifier = Modifier.fillMaxHeight(0.4f))
            Box(
                modifier = Modifier
                    //.background(Color.Green) //todo: hide this
                    //.align(alignment = Alignment.BottomCenter) ////////////////////
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .height(160.dp)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            //offset += dragAmount
                            //todo: change to this after collision is working
                            offset += Offset(x = dragAmount.x, y = 0f)
                        }
                    }
            ){
                // The Throw line
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                ){
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


