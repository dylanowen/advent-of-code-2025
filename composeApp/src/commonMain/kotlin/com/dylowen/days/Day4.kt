package com.dylowen.days

import com.dylowen.*

class Day4(input: String) : DaySolution {
    val grid = Grid2(
        input.asIterable().filter { it == '@' || it == '.' },
        input.indexOfFirst { it == '\n' }
    )

    override fun part1(): Problem = SimpleProblem {
        grid.tpEntries().count(grid::canRemove)
    }

    override fun part2(): Problem = SimpleProblem {
        var notDone = true
        var workingGrid = grid.copy()
        var removedRolls = 0
        while (notDone) {
            notDone = false
            val nextGrid = workingGrid.copy()
            for (pos in workingGrid.tpEntries()) {
                if (workingGrid.canRemove(pos)) {
                    notDone = true
                    nextGrid[pos] = '.'
                    removedRolls++
                }
            }
            workingGrid = nextGrid
        }
        removedRolls
    }

    companion object Companion : Day {

        override val day: Int = 4

        override fun sample(): DaySolution = Day4(
            """..@@.@@@@.
@@@.@.@.@@
@@@@@.@.@@
@.@@@@..@.
@@.@@@@.@@
.@@@@@@@.@
.@.@.@.@@@
@.@@@.@@@@
.@@@@@@@@.
@.@.@@@.@.
"""
        )

        override fun solution(input: String): DaySolution = Day4(input)
    }
}

fun Grid2<Char>.tpEntries() = this.entries().mapNotNull { (pos, value) ->
    if (value == '@') {
        pos
    }
    else {
        null
    }
}

fun Grid2<Char>.canRemove(pos: Pos2) = pos.neighbors().count { this[it] == '@' } < 4