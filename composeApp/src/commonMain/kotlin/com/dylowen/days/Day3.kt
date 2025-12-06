package com.dylowen.days

import com.dylowen.Day
import com.dylowen.DaySolution
import com.dylowen.Problem
import com.dylowen.SimpleProblem
import com.dylowen.pow

class Day3(input: String) : DaySolution {
    val batteries = input.trim().split("\n")
        .map {
            it.asIterable().map(Char::digitToInt)
        }

    override fun part1(): Problem = SimpleProblem {
        batteries.sumOf { findMaxJoltag(it, 2) }
    }

    override fun part2(): Problem = SimpleProblem {
        batteries.sumOf { findMaxJoltag(it, 12) }
    }

    private fun findMaxJoltag(row: List<Int>, totalBatteries: Int): Long {
        var batteries = totalBatteries
        var sum = 0L
        var i = 0
        while (batteries > 0) {
            batteries--
            val (max, maxIndex) = row.drop(i)
                .dropLast(batteries)
                .withIndex()
                .fold(Pair(0, -1)) { (max, maxIndex), (i, battery) ->
                    if (battery > max) {
                        Pair(battery, i)
                    } else {
                        Pair(max, maxIndex)
                    }
                }
            sum += max * 10L.pow(batteries)
            i += maxIndex + 1
        }

        return sum
    }

    companion object : Day {

        override val day: Int = 3

        override fun sample(): DaySolution = Day3(
            """987654321111111
811111111111119
234234234234278
818181911112111
"""
        )

        override fun solution(input: String): DaySolution = Day3(input)
    }
}
