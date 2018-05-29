package it.intre

import arrow.data.Try
import arrow.syntax.function.andThen
import arrow.syntax.function.curried

val DEAD_CELL = '.'
val ALIVE_CELL = '*'

data class World<out A>(val ll: List<List<A>>)
{
    fun tryGet(row:Int, column: Int):Try<A> {
        return Try { ll[row][column] }
    }

    fun <B>map(f : (A) -> B):World<B>   {
        return World(ll.map { list ->
            list.map { cell ->
                f(cell)
            }
        })
    }

    fun <B>mapIndexed(f : (A, Pair<Int, Int>) -> B):World<B>   {
        return World(ll.mapIndexed { row, list ->
            list.mapIndexed { col, cell ->
                f(cell, row to col)
            }
        })
    }

    companion object {
        fun <A>empty(): World<A> = World(listOf(listOf()))
        fun <A>of(ll: List<List<A>>): World<A> = World(ll)
    }
}

data class Cell(val state: Char, val aliveNs: Int)
data class Position(val row: Int, val column: Int)

val getCellValue = { world: World<Char>, position: Position ->
    world.tryGet(position.row, position.column)
         .fold({ DEAD_CELL }, { it })
}
.curried()

val translate = { first: Position,
                  other: Position ->
    Position(first.row + other.row, first.column + other.column)
}.curried()

fun evolve(world: World<Char>): World<Char> {
    return world
        .mapIndexed { cell, pair -> cell to Position(pair.first, pair.second) }
        .map { pair -> Cell(pair.first, countAliveNeighbours(world)(pair.second)) }
        .map { cell -> computeNextState(cell) }
}

val countAliveNeighbours = { world: World<Char>, position: Position ->
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

val computeNextState = { cell: Cell ->
    when (cell.aliveNs) {
        2 -> cell.state
        3 -> ALIVE_CELL
        else -> DEAD_CELL
    }
}

fun isAlive(cell: Char) = cell == ALIVE_CELL