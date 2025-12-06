package com.dylowen.days

import com.dylowen.*

class Day6(input: String) : DaySolution {
    val operations: List<Operation>
    val numStrings: List<List<String>>
    val numbers: List<List<Long>>

    init {
        val lines = input.lines()

        val opLine = lines.last()
        val tempOperations = mutableListOf<Operation>()
        var i = 0
        while (i >= 0 && i < opLine.length) {
            val op = opLine[i]
            var nextI = opLine.indexOfAny("*+".toCharArray(), i + 1)
            if (nextI == -1) {
                nextI = opLine.length + 1
            }
            tempOperations.add(Operation(op, nextI - i - 1)) // subtract 1 for the whitespace
            i = nextI // step past the whitespace
        }
        operations = tempOperations.toList()


        val verticalNumbers = lines.dropLast(1).map { row ->
            val tempRow = mutableListOf<String>()
            var i = 0
            for ((_, spacing) in operations) {
                tempRow.add(row.substring(i, i + spacing))
                i += spacing + 1
            }
            tempRow
        }
        val tempNumbers = verticalNumbers.first().toList().map { mutableListOf(it) }
        for (row in verticalNumbers.drop(1)) {
            for ((i, num) in row.withIndex()) {
                tempNumbers[i].add(num)
            }
        }
        numStrings = tempNumbers.map { it.toList() }

        numbers = numStrings.map { row -> row.map { it.trim().toLong() } }

    }

    override fun part1(): Problem = SimpleProblem {
        operations.withIndex().sumOf { (i, op) ->
            op(numbers[i])
        }
    }

    override fun part2(): Problem = SimpleProblem {
        operations.withIndex().sumOf { (i, op) ->
            val squidNums = (0 until op.numWidth).map { j ->
                numStrings[i].fold(0L) { acc, num ->
                    if (num[j] != ' ') {
                        acc * 10 + num[j].toString().toLong()
                    } else {
                        acc
                    }
                }
            }

            op(squidNums)
        }
    }

    companion object Companion : Day {

        override val day: Int = 6

        override fun sample(): DaySolution = Day6(
            """123 328  51 64 
 45 64  387 23 
  6 98  215 314
*   +   *   +  """
        )

        override fun solution(input: String): DaySolution = Day6(input)
    }
}

data class Operation(val op: Char, val numWidth: Int) {
    operator fun invoke(nums: List<Long>): Long = when (op) {
        '+' -> nums.sum()
        '*' -> nums.reduce { a, b -> a * b }
        else -> throw IllegalArgumentException("Invalid op: $op")
    }
}