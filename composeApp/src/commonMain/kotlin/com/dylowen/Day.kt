package com.dylowen

import advent_of_code_2025.composeapp.generated.resources.Res
import androidx.compose.runtime.Composable

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
    fun answer() {
        SelectableTextWithCopy(safeResult())
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