package com.dylowen.days

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dylowen.*
import kotlinx.coroutines.delay

class Day4(input: String) : DaySolution {
    val grid = Grid2(
        input.asIterable().filter { it == '@' || it == '.' },
        input.indexOfFirst { it == '\n' }
    )

    override fun part1(): Problem = SimpleProblem {
        filteredGrids().first().removedRolls(grid)
    }

    override fun part2(): Problem = object : Problem {

        override fun result(): Any {
            return filteredGrids().last().removedRolls(grid)
        }

        val cellSize = 3F
        val frameTime = 200L
        @Composable
        override fun render() {
            val iterator = remember { filteredGrids().iterator() }
            var currentGrid by remember { mutableStateOf(iterator.next()) }

            LaunchedEffect(Unit) {
                while (true) {
                    delay(frameTime)
                    if (iterator.hasNext()) {
                        currentGrid = iterator.next()
                    }
                }
            }

            Column {
                Canvas(modifier = Modifier.size((currentGrid.width * cellSize).dp, (grid.height * cellSize).dp)) {
                    for ((pos, value) in currentGrid.entries()) {
                        this.drawRect(
                            color = if (value == '@') Color.LightGray else Color.DarkGray,
                            topLeft = Offset(pos.x * cellSize, pos.y * cellSize),
                            size = Size(cellSize, cellSize)
                        )
                    }
                }
                SelectableTextWithCopy(currentGrid.removedRolls(grid).toString())
            }
        }
    }

    private fun filteredGrids() = sequence {
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
            yield(nextGrid)
            workingGrid = nextGrid
        }
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
    } else {
        null
    }
}

fun Grid2<Char>.canRemove(pos: Pos2) = pos.neighbors().count { this[it] == '@' } < 4

fun Grid2<Char>.removedRolls(other: Grid2<Char>) = this.entries().count { (pos, value) -> value != other[pos] }