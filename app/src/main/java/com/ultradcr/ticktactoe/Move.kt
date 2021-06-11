package com.ultradcr.ticktactoe

data class Move(
    val index: Int,
    val player: Player,
    var moveText: String =
        when (player) {
            Player.HUMAN -> "O"
            Player.COMPUTER -> "X"
        }

)
