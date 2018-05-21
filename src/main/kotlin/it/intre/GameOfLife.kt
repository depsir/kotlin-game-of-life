package it.intre

import arrow.syntax.function.andThen
import arrow.syntax.function.curried

typealias World = List<List<Char>>
data class Position(val row:Int, val column:Int)
val getPosition = {world: World, position: Position -> world.getOrNull(position.row)?.getOrNull(position.column) ?: '.'}.curried()
val translate = { first:Position, other: Position -> Position(first.row + other.row, first.column + other.column)}.curried()

fun evolve(world: World): World {
    return world.mapIndexed { row, list -> list.mapIndexed { col, cell ->
        (countAliveNeighbours(world) andThen evolveCell(cell))(Position(row,col))} }
}

val evolveCell = {cell:Char, numberOfAliveNeighbours:Int ->
     when (numberOfAliveNeighbours) {
        2 -> cell
        3 -> '*'
        else -> '.'
    }
}.curried()

val countAliveNeighbours = {world: World, position: Position ->
    val neighbourPositions = listOf(Position(-1,-1), Position(-1,0), Position(-1,1), Position(0,-1),Position(0,1),Position(1,-1),Position(1,0),Position(1,1))
    neighbourPositions
            .map( translate(position) andThen getPosition(world))
            .filter(::isAlive)
            .count()
}.curried()

fun isAlive(cell: Char) = cell == '*'
