package com.dylowen.days

import com.dylowen.Day
import com.dylowen.DaySolution
import com.dylowen.Problem
import com.dylowen.SimpleProblem

class Day1(input: String) : DaySolution {
    val turns = input.lines()
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .mapNotNull { line ->
            when {
                line.startsWith("L") -> Left(line.substringAfter("L").toInt())
                line.startsWith("R") -> Right(line.substringAfter("R").toInt())
                else -> null
            }
        }

    override fun part1(): Problem = SimpleProblem {
        turns.fold(Pair(50, 0)) { (acc, count), turn ->
            val next = (acc + turn.normalizedDistance + 100) % 100
            Pair(next, count + if (next == 0) 1 else 0)
        }.second
    }

    override fun part2(): Problem = SimpleProblem {
        turns.fold(Pair(50, 0)) { (acc, count), turn ->
            val next = ((acc + turn.normalizedDistance) % 100 + 100) % 100
            val bonusPasses = turn.distance / 100
            val passedZero = when (turn) {
                is Left if acc != 0 && next > acc -> true
                is Right if acc != 0 && next < acc -> true
                else -> next == 0
            }
            val zeroPasses = (if (passedZero) 1 else 0) + bonusPasses

            Pair(next, count + zeroPasses)
        }.second
    }

    companion object : Day {

        override val day: Int = 1

        override fun sample(): DaySolution = Day1(
            """L68
L30
R48
L5
R60
L55
L1
L99
R14
L82
"""
        )

        override fun solution(input: String): DaySolution = Day1(input)
    }
}

sealed interface Turn {
    val distance: Int
    val normalizedDistance: Int
}

data class Left(override val distance: Int) : Turn {
    override val normalizedDistance: Int
        get() = -distance
}

data class Right(override val distance: Int) : Turn {
    override val normalizedDistance: Int
        get() = distance
}