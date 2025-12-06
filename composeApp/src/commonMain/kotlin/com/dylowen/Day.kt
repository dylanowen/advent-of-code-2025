package com.dylowen

import advent_of_code_2025.composeapp.generated.resources.Res
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface Day {

    val day: Int

    suspend fun defaultInput(): String {
        return Res.readBytes("files/${day}.txt").decodeToString()
    }

    fun sample(): DaySolution? = null
    fun solution(input: String): DaySolution
}

interface DaySolution {
    fun part1(): Problem
    fun part2(): Problem
}

interface Problem {

    fun result(): Any

    private fun safeResult(): String = try {
        result().toString()
    } catch (e: Exception) {
        e.toString()
    }

    @Composable
    fun answer(modifier: Modifier = Modifier) {
        SelectableTextWithCopy(safeResult(), modifier)
    }

    @Composable
    fun render()
}

fun interface SimpleProblem : Problem {

    @Composable
    override fun render() {
       answer()
    }
}