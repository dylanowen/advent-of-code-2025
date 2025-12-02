package com.dylowen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.dylowen.days.*
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview

private val Days = listOf(Day1, Day2)

sealed interface Path {
    val title: String
}

@Serializable
object MainPath : Path {
    override val title: String = "Home"
}

@Serializable
data class DayPath(val day: Int) : Path {
    override val title: String
        get() = "Day $day"
}


@Composable
@Preview
fun App(
    onNavHostReady: suspend (NavController) -> Unit = {}
) {
    MaterialTheme {
        val navController = rememberNavController()

        val backStackEntry by navController.currentBackStackEntryAsState()
        val route = backStackEntry?.destination?.route ?: "com.dylowen.MainPath"
        val currentPath = when {
            route.startsWith("com.dylowen.DayPath") -> backStackEntry?.toRoute<DayPath>() ?: MainPath
            else -> MainPath
        }

        Scaffold(
            topBar = {
                TitleBar(
                    path = currentPath,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() })
            }) { innerPadding ->
            Box(modifier = Modifier.padding(14.dp)) {
                NavHost(
                    navController = navController,
                    startDestination = MainPath,
                    modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(innerPadding)
                ) {
                    composable<MainPath> {
                        Column {
                            Days.forEach { day ->
                                Button(onClick = { navController.navigate(DayPath(day.day)) }) {
                                    Text("Day ${day.day}")
                                }
                            }
                        }
                    }
                    composable<DayPath> { backStackEntry ->
                        val dayNum = backStackEntry.toRoute<DayPath>().day

                        Days.find { it.day == dayNum }?.let { day ->
                            DayView(day)
                        } ?: run {
                            Text("Unknown day: $dayNum")
                        }
                    }
                }
            }
        }
        LaunchedEffect(navController) {
            onNavHostReady(navController)
        }
    }
}

@Composable
fun DayView(day: Day) {
    var input by remember { mutableStateOf("") }

    Column {
        if (input.trim().isNotEmpty()) {
            val sample = runCatching { day.sample() }
            val solution = runCatching { day.solution(input) }

            Column {
                sample.mapCatching { it?.part1() }.fold(
                    onSuccess = { it?.render() }, onFailure = ::error
                )
                sample.mapCatching { it?.part2() }.fold(
                    onSuccess = { it?.render() }, onFailure = ::error
                )
                solution.mapCatching { it.part1() }.fold(
                    onSuccess = { it.render() }, onFailure = ::error
                )
                solution.mapCatching { it.part2() }.fold(
                    onSuccess = { it.render() }, onFailure = ::error
                )
            }
        } else {
            Text("Loading...")
        }
        OutlinedTextField(
            value = input, onValueChange = { input = it }, // User can edit after loading
            modifier = Modifier.fillMaxWidth().height(100.dp), textStyle = TextStyle(
                fontFamily = FontFamily.Monospace, fontSize = 14.sp
            ), maxLines = Int.MAX_VALUE, singleLine = false
        )
    }
    LaunchedEffect(Unit) {
        input = day.defaultInput()
    }
}

@Composable
fun error(exception: Throwable) {
    Text(exception.toString())
}

@Composable
fun TitleBar(
    path: Path, canNavigateBack: Boolean, navigateUp: () -> Unit, modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(path.title)
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ), modifier = modifier, navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back"
                    )
                }
            }
        })
}

@Composable
internal fun CircleProgress(
    angle: Float, modifier: Modifier = Modifier
) {
    Box(
        modifier.fillMaxSize().drawBehind {
            drawArc(
                color = Color.White, startAngle = 0f, sweepAngle = 360f, useCenter = false, style = Stroke(width = 30f)
            )
            drawArc(
                brush = Brush.verticalGradient(listOf(Color.Green, Color.Blue)),
                startAngle = -90f,
                sweepAngle = angle,
                useCenter = false,
                style = Stroke(width = 30f, cap = StrokeCap.Round)
            )
        })
}
