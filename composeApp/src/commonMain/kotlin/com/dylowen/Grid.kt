package com.dylowen

class Grid2<T>(private val cells: MutableList<T>, val width: Int) : Iterable<T> {

    constructor(cells: Iterable<T>, width: Int) : this(cells.toMutableList(), width)

    constructor(cells: Iterable<List<T>>) : this(cells.flatMap { it }, cells.firstOrNull()?.size ?: 0)

    val height: Int = (cells.size + width - 1) / width

    val widthRange: IntRange by lazy {
        (0 until width)
    }

    val heightRange: IntRange by lazy {
        (0 until height)
    }

    operator fun get(pos: Pos2): T? = get(pos.x, pos.y)

    operator fun get(x: Int, y: Int): T? = if (x in widthRange && y in heightRange) {
        cells[y * width + x]
    } else {
        null
    }

    operator fun set(x: Int, y: Int, value: T): T? = if (x in widthRange && y in heightRange) {
        val i = y * width + x
        val previous = cells[i]
        cells[i] = value
        previous
    } else {
        null
    }

    fun entries(): List<Entry2<T>> = cells.withIndex().map { (i, value) -> Entry2(indexToXy(i), value) }

    override fun iterator(): Iterator<T> = cells.iterator()

    private fun xyToIndex(x: Int, y: Int): Int = y * width + x

    private fun indexToXy(i: Int): Pos2 = Pos2(i.rem(width), i / width)
}

data class Pos2(val x: Int, val y: Int) {
    fun neighbors(dirs: List<Dir> = Dir.MANHATTAN): List<Pos2> = dirs.map(::neighbor)

    fun neighbor(dir: Dir): Pos2 = when (dir) {
        Dir.N -> Pos2(x, y - 1)
        Dir.NE -> Pos2(x + 1, y - 1)
        Dir.E -> Pos2(x + 1, y)
        Dir.SE -> Pos2(x + 1, y + 1)
        Dir.S -> Pos2(x, y + 1)
        Dir.SW -> Pos2(x - 1, y + 1)
        Dir.W -> Pos2(x - 1, y)
        Dir.NW -> Pos2(x - 1, y - 1)
    }
}

data class Entry2<T>(val pos: Pos2, val value: T)

enum class Dir {
    N, NE, E, SE, S, SW, W, NW;

    companion object {
        val MANHATTAN = listOf(N, E, S, W)
        val ALL = Dir.entries
    }
}