package com.ultradcr.ticktactoe

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

object AlertView {
    @Composable
    fun MyAlert(
        type: AlertType,
        onDismissRequest: () -> Unit,
    ) {
        when (type) {
            AlertType.ComputerWin -> AlertDialog(
                onDismissRequest = onDismissRequest,
                confirmButton = { },
                dismissButton = {
                    Button(
                        onClick = onDismissRequest
                    ) {
                        Text("Hell yeha")
                    }
                },
                title = { Text(text = "AI Wins") },
                text = { Text("You made a AI that can win against you ,good job.") })

            AlertType.Draw ->  AlertDialog(
                onDismissRequest = onDismissRequest,
                confirmButton = { },
                dismissButton = {
                    Button(
                        onClick = onDismissRequest
                    ) {
                        Text("Rematch")
                    }
                },
                title = { Text(text = "Match Draw") },
                text = { Text("You made a competitive AI good job.") })

            AlertType.HumanWin ->  AlertDialog(
                onDismissRequest = onDismissRequest,
                confirmButton = { },
                dismissButton = {
                    Button(
                        onClick = onDismissRequest
                    ) {
                        Text("Rematch")
                    }
                },
                title = { Text(text = "You Win") },
                text = { Text("You defeat your own AI ,Good job.") })

            AlertType.None -> { }
        }
    }


}