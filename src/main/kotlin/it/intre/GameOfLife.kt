package it.intre

import org.funktionale.currying.*
import org.funktionale.composition.*

val map: ((Int, Char, List<Char>) -> Char) -> (List<Char>) -> List<Char> = { transform: (kotlin.Int, Char, List<Char>) -> Char, collection: kotlin.collections.List<Char> ->
    collection.mapIndexed { idx: Int, e: Char -> transform.invoke(idx, e, collection) }
}.curried()
val flatten: (List<List<Char>>) -> List<Char> = { world: List<List<Char>> -> world.flatten()}
val windowed = { size: Int, step: Int, flatWorld:List<Char> ->
    flatWorld.windowed(size, step)
}.curried()

fun evolve(world: List<List<Char>>): List<List<Char>> {
    val worldWidth = world.first().size
    val evolveWorld = flatten andThen map(::evolveCell) andThen windowed(worldWidth)(worldWidth)
    return if (worldWidth == 0) world else evolveWorld(world)
}

fun evolveCell(idx: Int, cell: Char, world: List<Char>): Char = if (countAliveNeighbours(idx, world) == 2 ) '*' else '.'

fun countAliveNeighbours(idx: Int, world: List<Char>) =
        getNeighbours(idx, world)
                .filter(::isAlive)
                .count()

fun getNeighbours(idx: Int, world: List<Char>): List<Char> = neighborhood.map { it.invoke(idx, world)}
val neighborhood = listOf(::before, ::after)
fun before(idx: Int, world: List<Char>) = world.getOrElse(idx - 1, ::getNone)
fun after(idx: Int, world: List<Char>) = world.getOrElse(idx + 1, ::getNone)

fun getNone(idx: Int) = '.'

fun isAlive(cell: Char) = cell == '*'

