package com.ultradcr.ticktactoe

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ultradcr.ticktactoe.AlertView.MyAlert
import com.ultradcr.ticktactoe.ui.theme.*
import java.util.*
import kotlin.concurrent.timerTask

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    private val viewModel: TickTackToeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TickTacToeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting(viewModel = viewModel,"Tick Tac Toe")
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun Greeting(viewModel: TickTackToeViewModel,name: String) {
    Scaffold(
        topBar = {
            TopAppBar {
                Text(name, modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(), textAlign = TextAlign.Center)
            }
        }
    ) {
        var alertType by remember {
            mutableStateOf(AlertType.None)
        }
        val rows = remember { viewModel.rows }
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            DarkBlue,
                            DarkBlue2
                        )
                    )
                )
                .padding(2.dp)
                .fillMaxHeight()

            ,
            contentAlignment = Alignment.Center
        ) {

            var clickEnable by remember {
                mutableStateOf(true)
            }

            LazyVerticalGrid(cells = GridCells.Fixed(3)) {
                itemsIndexed(rows) { row, item ->
                    Box(
                        modifier = Modifier
                            .padding(15.dp)
                            .clip(CircleShape)
                            .aspectRatio(1F)
                            .background(CircleBg)
                            .clickable(enabled = clickEnable) {
                                if (viewModel.canMakeMove(rows, row)) {

                                    rows[row] = Move(row, Player.HUMAN)
                                    clickEnable = false

                                    if (viewModel.didWinTheGame(rows, Player.HUMAN)) {
                                        println("Win")
                                        alertType = AlertType.HumanWin
                                        clickEnable = true
                                        return@clickable
                                    }
                                    if (viewModel.isDraw(rows)) {
                                        println("Draw")
                                        alertType = AlertType.Draw
                                        clickEnable = true
                                        return@clickable
                                    }

                                    Timer().schedule(
                                        timerTask {
                                            val moveIndex = viewModel.computerAiComputationMove(rows)
                                            rows[moveIndex] = Move(moveIndex, Player.COMPUTER)

                                            if (viewModel.didWinTheGame(rows, Player.COMPUTER)) {
                                                println("Win")
                                                alertType = AlertType.ComputerWin
                                                clickEnable = true
                                            }

                                            clickEnable = true

                                        }, 500
                                    )

                                    Log.d("TAG", "Greeting: $rows")
                                }
                            }, contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item?.moveText ?: "",
                            color = Color.White,
                            style = Typography.h4,
                            textAlign = TextAlign.Center
                        )
                    }

                }
            }
        }
        MyAlert(type = alertType){
            for (i in 0..8) {
                rows[i] = null
            }
            alertType = AlertType.None
        }
    }


}




enum class Player {
    HUMAN,
    COMPUTER
}

enum class AlertType {
    ComputerWin,
    HumanWin,
    Draw,
    None
}


@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TickTacToeTheme {
        Greeting(viewModel = TickTackToeViewModel(),"Tic Tac Toe")
    }
}