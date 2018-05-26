package it.intre

import arrow.syntax.function.andThen
import arrow.syntax.function.curried

data class World<out A>(val ll: List<List<A>>)
{
    fun getOrNull(row:Int, column: Int) {
        ll.getOrNull(row)
                ?.getOrNull(column)
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

data class Cell(val state: Char, val aliveNs: Int)
data class Position(val row: Int, val column: Int)


val DEAD_CELL = '.'
val ALIVE_CELL = '*'
val getCellValue = { world: World<Char>, position: Position ->
    world.getOrNull(position.row, position.column)
            ?: DEAD_CELL
}
        .curried()
val translate = { first: Position,
                  other: Position ->
    Position(first.row + other.row, first.column + other.column)
}.curried()

fun evolve(world: World<Char>): World<Char> {
    return computeNextStates(toCell(world, addPosition(world)))
}

fun computeNextStates(w: World<Cell>): World<Char> {
    return w.map { cell ->
            computeNextState(cell)
        }
}

fun addPosition(world: World<Char>): World<Pair<Char, Position>> {
    return world.mapIndexed { cell, pair ->
            cell to Position(pair.first, pair.second)
    }
}


fun toCell(world: World<Char>, ll: World<Pair<Char, Position>>): World<Cell> {
    return ll.map { pair ->
            Cell(pair.first, countAliveNeighbours(world)(pair.second))
    }
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
