package it.intre

import arrow.syntax.function.andThen
import arrow.syntax.function.curried

typealias World = List<List<Char>>

data class Position(val row: Int, val column: Int)

val DEAD_CELL = '.'
val ALIVE_CELL = '*'
val getCellValue = { world: World, position: Position ->
    world.getOrNull(position.row)
            ?.getOrNull(position.column)
            ?: DEAD_CELL
}
        .curried()
val translate = { first: Position, other: Position -> Position(first.row + other.row, first.column + other.column) }.curried()

fun evolve(world: World): World {
    return world.mapIndexed { row, list ->
        list.mapIndexed { col, cell ->
            val evolveCell = countAliveNeighbours(world) andThen computeNextState(cell)
            evolveCell(Position(row, col))
        }
    }
}

val countAliveNeighbours = { world: World, position: Position ->
    (-1..1).flatMap { x ->
        (-1..1).map { y ->
            x to y
        }
    }
            .filter { (x, y) -> x != 0 || y != 0 }
            .map { (x, y) -> Position(x, y) }
            .map(translate(position) andThen getCellValue(world))
            .filter(::isAlive)
            .count()
}.curried()

val computeNextState = { cell: Char, numberOfAliveNeighbours: Int ->
    when (numberOfAliveNeighbours) {
        2 -> cell
        3 -> ALIVE_CELL

        else -> DEAD_CELL
    }
}.curried()

fun isAlive(cell: Char) = cell == ALIVE_CELL
