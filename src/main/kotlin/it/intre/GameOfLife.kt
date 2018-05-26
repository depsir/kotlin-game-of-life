package it.intre

import arrow.data.Try
import arrow.syntax.function.andThen
import arrow.syntax.function.curried

typealias World = List<List<Char>>

data class Position(val row: Int, val column: Int)

val getCellValue = { world: World, position: Position ->
    Try {
        world[position.row][position.column]
    }.fold(
            { '.' },
            { it }
    )
}.curried()

val translate = { first:Position, other: Position -> Position(first.row + other.row, first.column + other.column)}.curried()

fun evolve(world: World): World {
    return world.mapIndexed { row, list -> list.mapIndexed { col, cell ->
        val evolveCell = countAliveNeighbours(world) andThen computeNextState(cell)
        evolveCell(Position(row,col))
    }}
}

val countAliveNeighbours = {world: World, position: Position ->
    val neighbourPositions = listOf(Position(-1,-1), Position(-1,0), Position(-1,1), Position(0,-1),Position(0,1),Position(1,-1),Position(1,0),Position(1,1))
    neighbourPositions
            .map( translate(position) andThen getCellValue(world))
            .filter(::isAlive)
            .count()
}.curried()

val computeNextState = { cell:Char, numberOfAliveNeighbours:Int ->
    when (numberOfAliveNeighbours) {
        2 -> cell
        3 -> '*'
        else -> '.'
    }
}.curried()

fun isAlive(cell: Char) = cell == '*'
