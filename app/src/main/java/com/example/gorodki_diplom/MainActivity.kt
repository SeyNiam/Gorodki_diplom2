package com.example.gorodki_diplom

// todo: these things:
/*
- change icon (res/drawable and/or mipmap?)
res/values:
    + add cool colours with names (no lame numbers)
    - add text through strings
    - figure out what themes are
- figure out all in res/xml folder
- figure out all in ui.theme folder (and res/values relation. contradictions?)
- there was a game option in c++, but for later android versions. right choice?
- i can add c++ tp module (file). look into it
- fun stuff in tools

+ menu
+ game itself (at least 1 lvl)
- option to stop & quit & stuff
- login, reg
- account/profile page
- leaderboard
- all af main game
- settings (sound & smth else)
- multiplayer if have time
- themes? (bg, colours, etc)
*/

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.example.gorodki_diplom.ui.theme.Gorodki_diplomTheme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.delay

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.*

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.drawscope.translate
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

// Text lines
const val rulesText =
    "Чтобы перемещать биту по горизонтали, нажмите на неё пальцем и тяните.\n\n" +
            "Для того, чтобы бросить биту нажмите на экран.\n\n" +
            "При попадании битой в городок -- он считается сбитым.\n\n" +
            "Для прохождения уровня сбейте все городки!\n\n"

// Colours
const val darkCyan = 0xFF008080
const val darkerCyan = 0xFF004040
const val tableLightCyan = 0x4400b0b0




var lvl = 1









// Reusable Title text
@Composable
fun PrintTileText(text: String, modifier: Modifier = Modifier) {
    Text(
            text = text,
            modifier = modifier,
            fontSize = 44.sp,
            fontWeight = FontWeight.Light,
            color = Color(darkerCyan)
    )
}

// Reusable Any text
@Composable
fun PrintAnyText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = 28.sp,
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
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
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
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
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
    Menu, Profile, Settings, CGame, Leaderboard, Rules
}

//@Preview(widthDp = 400, heightDp = 800, showBackground = true)
@Composable
fun MenuScreen() {
    var currentScreen by remember { mutableStateOf(Screen.Menu) }
    when (currentScreen) {
        Screen.Menu -> HomeScreen(
            onProfileClick = { currentScreen = Screen.Profile },
            onSettingsClick = { currentScreen = Screen.Settings },
            onCGameClick = { currentScreen = Screen.CGame },
            onLBClick = { currentScreen = Screen.Leaderboard },
            onRulesClick = { currentScreen = Screen.Rules }
        )
        Screen.Profile -> ProfileScreen { currentScreen = Screen.Menu }
        Screen.Settings -> SettingsScreen { currentScreen = Screen.Menu }
        Screen.CGame -> CGameScreen { currentScreen = Screen.Menu }
        Screen.Leaderboard -> LBScreen { currentScreen = Screen.Menu }
        Screen.Rules -> RulesScreen { currentScreen = Screen.Menu }
    }
}

@Composable
fun HomeScreen(onProfileClick: () -> Unit, onSettingsClick: () -> Unit, onCGameClick: () -> Unit,
               onLBClick:() -> Unit, onRulesClick: () -> Unit) {
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
            CustomMenuButton(text = "Рекорды", onClick = onLBClick)
            Box(modifier = Modifier.fillMaxHeight(fraction = 0.04f))
            CustomMenuButton(text = "Правила", onClick = onRulesClick)
        }
    }
}




// todo: ATM -- ProfileScreen
@Preview(widthDp = 400, heightDp = 800, showBackground = true)
@Composable
fun TestScreen(){
    Box(modifier = Modifier
        .padding(32.dp, 0.dp, 32.dp, 32.dp)
    ){
        Column {
            Box(modifier = Modifier
                .fillMaxHeight(0.1f)
                .fillMaxWidth()
            ){
                PrintTileText(text = "ПРОФИЛЬ", Modifier.align(Alignment.Center))
            }
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 32.dp, 0.dp, 0.dp)
            ){
                // Draft in figma
            }
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
}

@Composable
fun LBScreen(onScreenChange: () -> Unit) {
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
            Box(modifier = Modifier
                .padding(32.dp, 0.dp, 32.dp, 32.dp)
            ){
                Column {
                    Box(modifier = Modifier
                        .fillMaxHeight(0.1f)
                        .fillMaxWidth()
                    ){
                        PrintTileText(text = "РЕКОРДЫ", Modifier.align(Alignment.Center))
                    }
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp, 32.dp, 0.dp, 0.dp)
                    ){

                        // todo: List from DB
                        /*
                        fun TestScreen(names: List<String>, scores: List<Int>) {

                        TestScreen(names = names, scores = scores)
                        */
                        val names = listOf("sht0ra", "I2oony", "Pe5ha", "forest crow", "SeyNiam", "OKta")
                        val scores = listOf(5000, 1200, 900, 210, 160, 50)

                        // Table
                        LazyColumn(
                            modifier = Modifier.border(1.dp, Color(darkerCyan))
                        ) {
                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(darkCyan))
                                        .padding(8.dp)
                                ) {
                                    Text(text = "Имя", modifier = Modifier.weight(1f), color = Color.White, fontSize = 20.sp)
                                    Text(text = "Счёт", modifier = Modifier.weight(1f), color = Color.White, fontSize = 20.sp)
                                }
                            }
                            items(names.size) { index ->
                                val backgroundColor = if (index % 2 == 0) Color.White else Color(tableLightCyan)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(backgroundColor)
                                        .padding(8.dp)
                                ) {
                                    Text(text = names[index], modifier = Modifier.weight(1f))
                                    Text(text = scores[index].toString(), modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}

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
            Box(modifier = Modifier
                .padding(32.dp, 0.dp, 32.dp, 32.dp)
            ){
                Column {
                    Box(modifier = Modifier
                        .fillMaxHeight(0.1f)
                        .fillMaxWidth()
                    ){
                        PrintTileText(text = "ПРАВИЛА", Modifier.align(Alignment.Center))
                    }
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp, 32.dp, 0.dp, 0.dp)
                    ){
                        PrintAnyText(text = rulesText)
                    }
                }
            }
        }
    }
}



fun doesLineIntersectRectangle(lineStart: Offset, lineEnd: Offset, rectTopLeft: Offset, rectSize: Size): Boolean {
    val rectBottomRight = Offset(x = rectTopLeft.x + rectSize.width, y = rectTopLeft.y + rectSize.height)

    // Линия за границами прямоугольника
    if (lineStart.x < rectTopLeft.x && lineEnd.x < rectTopLeft.x) return false
    if (lineStart.x > rectBottomRight.x && lineEnd.x > rectBottomRight.x) return false
    if (lineStart.y < rectTopLeft.y && lineEnd.y < rectTopLeft.y) return false
    if (lineStart.y > rectBottomRight.y && lineEnd.y > rectBottomRight.y) return false

    // Линия пересекается с какой-либо из сторон прямоугольника
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
    val cos = cos(radians)
    val sin = sin(radians)
    return Offset(x = (x * cos - y * sin).toFloat(), y = (x * sin + y * cos).toFloat())
}


@Preview(widthDp = 400, heightDp = 800, showBackground = true)
@Composable
fun TestNCGameScreen() {
    var angle by remember { mutableFloatStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    var bitaLinePosition by remember { mutableStateOf(Offset.Zero) }
    var isDragging by remember { mutableStateOf(true) }

    var verticalOffset = 600f

    var throwCloser = false
    var throwDist = 0f

    var isFirstRectVisible by remember { mutableStateOf(true) }
    var isSecondRectVisible by remember { mutableStateOf(true) }
    var isThirdRectVisible by remember { mutableStateOf(true) }
    var isFourthRectVisible by remember { mutableStateOf(true) }
    var isFifthRectVisible by remember { mutableStateOf(true) }



    var lineStart = Offset(x = 0f, y = 0f).rotate(angle)
    var lineEnd = Offset(x = 0f, y = 0f).rotate(angle)

    var firstRectTopLeft = Offset(x = 0f, y = 0f)
    var firstRectSize = Size(width = 0f, height = 0f)
    var secondRectTopLeft = Offset(x = 0f, y = 0f)
    var secondRectSize = Size(width = 0f, height = 0f)
    var thirdRectTopLeft = Offset(x = 0f, y = 0f)
    var thirdRectSize = Size(width = 0f, height = 0f)
    var fourthRectTopLeft = Offset(x = 0f, y = 0f)
    var fourthRectSize = Size(width = 0f, height = 0f)
    var fifthRectTopLeft = Offset(x = 0f, y = 0f)
    var fifthRectSize= Size(width = 0f, height = 0f)

    fun checkIntersections() {
        if(isFirstRectVisible){
            isFirstRectVisible = !doesLineIntersectRectangle(lineStart, lineEnd, firstRectTopLeft, firstRectSize)
        }
        if(isSecondRectVisible){
            isSecondRectVisible = !doesLineIntersectRectangle(lineStart, lineEnd, secondRectTopLeft, secondRectSize)
        }
        if(isThirdRectVisible){
            isThirdRectVisible = !doesLineIntersectRectangle(lineStart, lineEnd, thirdRectTopLeft, thirdRectSize)
        }
        if(isFourthRectVisible){
            isFourthRectVisible = !doesLineIntersectRectangle(lineStart, lineEnd, fourthRectTopLeft, fourthRectSize)
        }
        if(isFifthRectVisible){
            isFifthRectVisible = !doesLineIntersectRectangle(lineStart, lineEnd, fifthRectTopLeft, fifthRectSize)
        }
    }





    LaunchedEffect(Unit) {
        while (true) {
            angle = (angle + 2) % 360
            delay(8)
        }
    }
    LaunchedEffect(isDragging) {
        if (!isDragging) {
            val tmp = bitaLinePosition.y
            while (bitaLinePosition.y > -100) {
                bitaLinePosition = bitaLinePosition.copy(y = bitaLinePosition.y - 1)
                offset += Offset(x = 0f, y = bitaLinePosition.y - 1)
                checkIntersections()
                delay(32)
            }
            bitaLinePosition = Offset.Zero
            offset = Offset(x = bitaLinePosition.x, y = tmp)
            isDragging = true

            // Все невидимые
            if(!isFirstRectVisible && !isSecondRectVisible && !isThirdRectVisible &&
                !isFourthRectVisible && !isFifthRectVisible){
                isFirstRectVisible = true
                isSecondRectVisible = true
                isThirdRectVisible = true
                isFourthRectVisible = true
                isFifthRectVisible = true

                lvl++
                if(lvl>3){lvl=1} // todo: fix when more levels

                throwDist = 0f
                offset += Offset(x = 0f, y = bitaLinePosition.y)
                throwCloser = false

                firstRectTopLeft = Offset(x = 0f, y = 0f)
                firstRectSize = Size(width = 0f, height = 0f)
                secondRectTopLeft = Offset(x = 0f, y = 0f)
                secondRectSize = Size(width = 0f, height = 0f)
                thirdRectTopLeft = Offset(x = 0f, y = 0f)
                thirdRectSize = Size(width = 0f, height = 0f)
                fourthRectTopLeft = Offset(x = 0f, y = 0f)
                fourthRectSize = Size(width = 0f, height = 0f)
                fifthRectTopLeft = Offset(x = 0f, y = 0f)
                fifthRectSize= Size(width = 0f, height = 0f)
            }
            /*
            else if(!throwCloser){ // Хотя бы 1 ещё видимый
                throwDist = 400f
                offset += Offset(x = 0f, y = bitaLinePosition.y - 400)
                throwCloser = true
            }*/
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    if (isDragging) {
                        offset += Offset(x = dragAmount.x, y = 0f) // todo: dragAmount.y to y=0f
                    }
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        isDragging = false
                    }
                )
            }
    ) {

        Column {
            Box(
                modifier = Modifier
                    //.background(Color.Green)
                    .fillMaxHeight()
                    .fillMaxWidth()
            ){

                Canvas(modifier = Modifier
                    .fillMaxSize()
                    /*
                    .graphicsLayer(
                        translationX = 0f,
                        translationY = verticalOffset + throwDist
                    )*/
                ){
                    val canvasWidth = size.width
                    val canvasHeight = size.height

                    // Throw line todo: move it according to the rules
                    drawLine(
                        color = Color.White,
                        start = Offset(x = -canvasWidth, y = canvasHeight / 2 + verticalOffset - throwDist),
                        end = Offset(x = canvasWidth, y = canvasHeight / 2 + verticalOffset - throwDist),
                        strokeWidth = 4.0f
                    )

                    /*
                    translate(left = canvasWidth / 2, top = canvasHeight / 2 + verticalOffset) {
                        if(!throwCloser){
                            drawLine(
                                color = Color.White,
                                start = Offset(x = -canvasWidth, y = 0f),
                                end = Offset(x = canvasWidth, y = 0f),
                                strokeWidth = 4.0f
                            )
                        }
                        else {
                            drawLine(
                                color = Color.White,
                                start = Offset(x = -canvasWidth, y = 0f - throwDist),
                                end = Offset(x = canvasWidth, y = 0f - throwDist),
                                strokeWidth = 4.0f
                            )
                        }
                    }*/
                }



                // Static objects
                Canvas(modifier = Modifier
                    .fillMaxSize()
                ){
                    val canvasWidth = size.width
                    val canvasHeight = size.height



                    // Town box
                    drawLine( // Top
                        color = Color.White,
                        start = Offset(x = canvasWidth/4, y = canvasWidth/6 - canvasWidth/16),
                        end = Offset(x = canvasWidth/2+canvasWidth/4 + canvasWidth/64, y = canvasWidth/6 - canvasWidth/16),
                        strokeWidth = 4.0f
                    )
                    drawLine( // Bottom
                        color = Color.White,
                        start = Offset(x = canvasWidth/4, y = canvasWidth/2 + canvasWidth/8),
                        end = Offset(x = canvasWidth/2+canvasWidth/4 + canvasWidth/64, y = canvasWidth/2 + canvasWidth/8),
                        strokeWidth = 4.0f
                    )
                    drawLine( //Left
                        color = Color.White,
                        start = Offset(x = canvasWidth/4, y = canvasWidth/6 - canvasWidth/16),
                        end = Offset(x = canvasWidth/4, y = canvasWidth/2 + canvasWidth/8),
                        strokeWidth = 4.0f
                    )
                    drawLine( // Right
                        color = Color.White,
                        start = Offset(x = canvasWidth/2+canvasWidth/4 + canvasWidth/64, y = canvasWidth/6 - canvasWidth/16),
                        end = Offset(x = canvasWidth/2+canvasWidth/4 + canvasWidth/64, y = canvasWidth/2 + canvasWidth/8),
                        strokeWidth = 4.0f
                    )

                    // Rectangles levels todo: make more
                    if(lvl==1){
                        firstRectTopLeft = Offset(x = canvasWidth / 2 - (canvasWidth / 60)-60*2, y = canvasWidth / 3+40)
                        firstRectSize = Size(width = canvasHeight / 40, height = canvasWidth / 60)
                        secondRectTopLeft = Offset(x = canvasWidth / 2 - (canvasWidth / 60)+60*2, y = canvasWidth / 3+40)
                        secondRectSize = Size(width = canvasHeight / 40, height = canvasWidth / 60)
                        thirdRectTopLeft = Offset(x = canvasWidth / 2 - (canvasWidth / 60), y = canvasWidth / 3+40)
                        thirdRectSize = Size(width = canvasHeight / 40, height = canvasWidth / 60)
                        fourthRectTopLeft = Offset(x = canvasWidth / 2 - (canvasWidth / 60)+60, y = canvasWidth / 3)
                        fourthRectSize = Size(width = canvasHeight / 40, height = canvasWidth / 60)
                        fifthRectTopLeft = Offset(x = canvasWidth / 2 - (canvasWidth / 60)-60, y = canvasWidth / 3)
                        fifthRectSize = Size(width = canvasHeight / 40, height = canvasWidth / 60)
                    }

                    if(lvl==2){
                        firstRectTopLeft = Offset(x = (canvasWidth / 3), y = (canvasHeight / 6))
                        firstRectSize = Size(width = (canvasWidth / 60), height = (canvasHeight / 40))
                        secondRectTopLeft = Offset(x = (canvasWidth / 6 + canvasWidth / 2), y = (canvasHeight / 6))
                        secondRectSize = Size(width = (canvasWidth / 60), height = (canvasHeight / 40))
                        thirdRectTopLeft = Offset(x = (canvasWidth / 2), y = (canvasHeight / 6))
                        thirdRectSize = Size(width = (canvasWidth / 60), height = (canvasHeight / 40))
                        fourthRectTopLeft = Offset(x = (canvasWidth / 2 - canvasWidth / 60), y = (canvasHeight / 4))
                        fourthRectSize = Size(width = (canvasHeight / 40), height = (canvasWidth / 60))
                        fifthRectTopLeft = Offset(x = (canvasWidth / 2 - canvasWidth / 60), y = (canvasHeight / 10))
                        fifthRectSize = Size(width = (canvasHeight / 40), height = (canvasWidth / 60))
                    }

                    if(lvl==3){
                        firstRectTopLeft = Offset(x = canvasWidth / 2 , y = canvasWidth / 3)
                        firstRectSize = Size(width = canvasWidth / 60, height = canvasHeight / 40)
                        secondRectTopLeft = Offset(x = canvasWidth / 2 - (canvasWidth / 60), y = canvasWidth / 3-40f)
                        secondRectSize = Size(width = canvasHeight / 40, height = canvasWidth / 60)
                        thirdRectTopLeft = Offset(x = canvasWidth / 2 - (canvasWidth / 60), y = canvasWidth / 2-60)
                        thirdRectSize = Size(width = canvasHeight / 40, height = canvasWidth / 60)
                        fourthRectTopLeft = Offset(x = canvasWidth / 2 - (canvasWidth / 60)+32, y = canvasWidth / 2-100)
                        fourthRectSize = Size(width = canvasHeight / 40, height = canvasWidth / 60)
                        fifthRectTopLeft = Offset(x = canvasWidth / 2 - (canvasWidth / 60)-32, y = canvasWidth / 2-100)
                        fifthRectSize = Size(width = canvasHeight / 40, height = canvasWidth / 60)
                    }

                    if(isFirstRectVisible){
                        drawRect(
                            color = Color.White,
                            topLeft = firstRectTopLeft,
                            size = firstRectSize
                        )
                    }
                    if(isSecondRectVisible){
                        this.drawRect(
                            color = Color.White,
                            topLeft = secondRectTopLeft,
                            size = secondRectSize
                        )
                    }
                    if(isThirdRectVisible){
                        drawRect(
                            color = Color.White,
                            topLeft = thirdRectTopLeft,
                            size = thirdRectSize
                        )
                    }
                    if(isFourthRectVisible){
                        drawRect(
                            color = Color.White,
                            topLeft = fourthRectTopLeft,
                            size = fourthRectSize
                        )
                    }
                    if(isFifthRectVisible){
                        drawRect(
                            color = Color.White,
                            topLeft = fifthRectTopLeft,
                            size = fifthRectSize
                        )
                    }

                }

                // Bita line with checking if intersected
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(
                            translationX = bitaLinePosition.x + offset.x,
                            translationY = bitaLinePosition.y + offset.y + verticalOffset,
                            rotationZ = angle
                        )
                ) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height

                    // Rect positions todo: make it all into a class or something
                    if (lvl==1){

                    }
                    if(lvl==2){
                        firstRectTopLeft = Offset(x = (canvasWidth / 3), y = (canvasHeight / 6))
                        firstRectSize = Size(width = (canvasWidth / 60), height = (canvasHeight / 40))
                        secondRectTopLeft = Offset(x = (canvasWidth / 6 + canvasWidth / 2), y = (canvasHeight / 6))
                        secondRectSize = Size(width = (canvasWidth / 60), height = (canvasHeight / 40))
                        thirdRectTopLeft = Offset(x = (canvasWidth / 2), y = (canvasHeight / 6))
                        thirdRectSize = Size(width = (canvasWidth / 60), height = (canvasHeight / 40))
                        fourthRectTopLeft = Offset(x = (canvasWidth / 2 + canvasWidth / 60), y = (canvasHeight / 4))
                        fourthRectSize = Size(width = (canvasWidth / 40), height = (canvasHeight / 60))
                        fifthRectTopLeft = Offset(x = (canvasWidth / 2 + canvasWidth / 60), y = (canvasHeight / 10))
                        fifthRectSize = Size(width = (canvasWidth / 40), height = (canvasHeight / 60))
                    }
                    if(lvl==3){

                    }



                    lineStart = Offset(x = -canvasWidth / 6, y = 0f).rotate(angle) + Offset(x = canvasWidth / 2, y = canvasHeight / 2) + offset + Offset(x = 0f, y = verticalOffset)
                    lineEnd = Offset(x = canvasWidth / 6, y = 0f).rotate(angle) + Offset(x = canvasWidth / 2, y = canvasHeight / 2) + offset + Offset(x = 0f, y = verticalOffset)


                    checkIntersections()


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


