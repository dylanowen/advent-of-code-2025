package com.dylowen.days

import com.dylowen.Day
import com.dylowen.DaySolution
import com.dylowen.Problem
import com.dylowen.SimpleProblem

class Day2(input: String) : DaySolution {
    val ranges = input.trim().split(",")
        .map {
            val (left, right) = it.split("-")
            left.toLong()..right.toLong()
        }

    override fun part1(): Problem = SimpleProblem {
        ranges.sumOf { range ->
            range.sumOf { i ->
                val s = i.toString()
                val mid = s.length / 2
                if (s.length % 2 == 0 && s.take(mid) == s.substring(mid)) {
                    i
                }
                else {
                    0
                }
            }
        }
    }

    override fun part2(): Problem = SimpleProblem {
        fun isInvalid(i: Long): Boolean {
            val s = i.toString()
            for (j in 1..s.length / 2) {
                if (s.chunked(j).toSet().size == 1) {
                    return true
                }
            }

            return false
        }

        ranges.sumOf { range ->
            range.sumOf { i ->
                if (isInvalid(i)) {
                    i
                }
                else {
                    0
                }
            }
        }
    }

    companion object : Day {

        override val day: Int = 2

        override fun sample(): DaySolution = Day2(
            """11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"""
        )

        override fun solution(input: String): DaySolution = Day2(input)
    }
}