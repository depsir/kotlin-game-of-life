package it.intre

import arrow.syntax.function.andThen
import arrow.syntax.function.curried

typealias World = List<List<Char>>
data class Position(val row:Int, val column:Int)
val getPosition = {world: World, position: Position -> world.getOrNull(position.row)?.getOrNull(position.column) ?: '.'}.curried()
val translate = { first:Position, other: Position -> Position(first.row + other.row, first.column + other.column)}.curried()

fun evolve(world: World): World {
    return world.mapIndexed { row, list -> list.mapIndexed { col, _ -> evolveCell(world, Position(row, col))} }
}

fun evolveCell(world: World, position:Position): Char = when (countAliveNeighbours(world, position)) {
    2 -> getPosition(world)(position)
    3 -> '*'
    else -> '.'
}

fun countAliveNeighbours(world: World, position: Position): Any {
    val neighbourPositions = listOf(Position(-1,-1), Position(-1,0), Position(-1,1), Position(0,-1),Position(0,1),Position(1,-1),Position(1,0),Position(1,1))
    return neighbourPositions
            .map( translate(position) andThen getPosition(world))
            .filter(::isAlive)
            .count()
}

fun isAlive(cell: Char) = cell == '*'
