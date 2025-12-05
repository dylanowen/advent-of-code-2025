package com.dylowen.days

import com.dylowen.Day
import com.dylowen.DaySolution
import com.dylowen.Problem
import com.dylowen.SimpleProblem
import kotlin.math.max
import kotlin.math.min

class Day5(input: String) : DaySolution {
    val ranges: List<LongRange>
    val ingredients: List<Long>

    init {
        val (rawRanges, rawIngredients) = input.split("\n\n")

        ranges = (rawRanges.split("\n").map {
            val (left, right) = it.split("-")
            left.toLong()..right.toLong()
        })
            .sortedBy { range -> range.first }
        ingredients = rawIngredients.split("\n").map { it.toLong() }
    }

    override fun part1(): Problem = SimpleProblem {
        ingredients.filter {
            for (range in ranges) {
                if (range.contains(it)) {
                    return@filter true
                }
            }

            false
        }
            .count()
    }

    override fun part2(): Problem = SimpleProblem {
        val distinctRanges = ranges.toMutableList()
        main@ while (true) {
            for ((ai, a) in distinctRanges.withIndex()) {
                for ((bi, b) in distinctRanges.withIndex().filter { (bi, _) -> bi != ai }) {
                    if (a.contains(b.first) || a.contains(b.last) || b.contains(a.first) || b.contains(a.last)) {
                        distinctRanges.removeAt(bi)
                        distinctRanges[ai] = min(a.first, b.first)..max(a.last, b.last)

                        continue@main
                    }
                }
            }

            break
        }

        distinctRanges.sumOf { it.last - it.first + 1 }
    }

    companion object Companion : Day {

        override val day: Int = 5

        override fun sample(): DaySolution = Day5(
            """3-5
10-14
16-20
12-18

1
5
8
11
17
32"""
        )

        override fun solution(input: String): DaySolution = Day5(input)
    }
}