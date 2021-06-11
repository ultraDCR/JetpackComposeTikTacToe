package com.ultradcr.ticktactoe

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class TickTackToeViewModel : ViewModel() {


    var rows = mutableStateListOf<Move?>(null, null, null, null, null, null, null, null, null)

    fun didWinTheGame(moves: MutableList<Move?>, player: Player): Boolean {
        val winSets = setOf(
            setOf(0, 1, 2),
            setOf(3, 4, 5),
            setOf(6, 7, 8),
            setOf(0, 3, 6),
            setOf(1, 4, 7),
            setOf(2, 5, 8),
            setOf(0, 4, 8),
            setOf(2, 4, 6)
        )

        val playerSet = moves.mapNotNull { it }.filter { it.player == player }.map { it.index }

        for (winSet in winSets) {
            if (playerSet.containsAll(winSet)) return true
        }

        return false
    }

    fun isDraw(moves: MutableList<Move?>): Boolean {
        return !moves.contains(null)
    }

    fun computerAiComputationMove(moves: MutableList<Move?>): Int {

        //if ai can win then, win
        val winSets = setOf(
            setOf(0, 1, 2),
            setOf(3, 4, 5),
            setOf(6, 7, 8),
            setOf(0, 3, 6),
            setOf(1, 4, 7),
            setOf(2, 5, 8),
            setOf(0, 4, 8),
            setOf(2, 3, 6)
        )

        val computerMoves = moves.mapNotNull { it }.filter { it.player == Player.COMPUTER }
        val computerPosition = computerMoves.map { it.index }

        for (winSet in winSets) {
            val winPosition = winSet.subtract(computerPosition)
            if (winPosition.size == 1) {
                val isAvailable = canMakeMove(moves, winPosition.first())
                if (isAvailable) {
                    return winPosition.first()
                }
            }
        }

        //if ai can't win then , block

        val humanMoves = moves.mapNotNull { it }.filter { it.player == Player.HUMAN }
        val humanPosition = humanMoves.map { it.index }

        for (winSet in winSets) {
            val winPosition = winSet.subtract(humanPosition)
            if (winPosition.size == 1) {
                val isAvailable = canMakeMove(moves, winPosition.first())
                if (isAvailable) {
                    return winPosition.first()
                }
            }
        }

        //if ai can't block then take middle one
        if (canMakeMove(moves, 4)) {
            return 4
        }

        //if ai can't take middle then make random move.
        var movePosition = Random.nextInt(0, 8)
        while (!canMakeMove(moves, movePosition)) {
            movePosition = Random.nextInt(0, 8)
        }

        return movePosition
    }

    fun canMakeMove(moves: MutableList<Move?>, index: Int): Boolean {
        return moves[index] == null
    }


}