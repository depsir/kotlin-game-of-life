package it.intre

import org.funktionale.currying.*
import org.funktionale.composition.*
import org.funktionale.partials.partially1

val map = { transform: (kotlin.Int, Char, List<Char>) -> Char, collection: kotlin.collections.List<Char> ->
    collection.mapIndexed { idx: Int, e: Char -> transform.invoke(idx, e, collection) }
}.curried()
val flatten = { world: List<List<Char>> -> world.flatten()}
val windowed = { size: Int, step: Int, flatWorld:List<Char> ->
    flatWorld.windowed(size, step)
}.curried()

fun evolve(world: List<List<Char>>): List<List<Char>> {
    val worldWidth = world.first().size
    val evolveWorld = flatten andThen map(evolveCell.partially1(worldWidth)) andThen windowed(worldWidth)(worldWidth)
    return if (worldWidth == 0) world else evolveWorld(world)
}

val evolveCell = {worldWidth:Int, idx: Int, cell: Char, world: List<Char> ->
    if (countAliveNeighbours(worldWidth, idx, world) == 2) cell else '.'}

fun countAliveNeighbours(worldWidth:Int, idx: Int, world: List<Char>) =
        getNeighbours(worldWidth, idx, world)
                .filter(::isAlive)
                .count()

fun getNeighbours(worldWidth:Int, idx: Int, world: List<Char>): List<Char> = neighborhood.map { it.invoke(worldWidth)(idx)(world)}
val neighborhood = listOf(::before, ::after, ::up, ::down)
fun before(worldWidth:Int) = getWithOffset(- 1)(isOnLeftEdge(worldWidth))
fun after(worldWidth:Int) = getWithOffset(+ 1)(isOnRightEdge(worldWidth))
fun up(worldWidth:Int) = getWithOffset(- worldWidth)(isOnFirstRow(worldWidth))
fun down(worldWidth:Int) = getWithOffset(+ worldWidth)({_:Int -> false})

val isOnRightEdge = {worldWidth: Int, idx: Int -> (idx + 1) % worldWidth == 0}.curried()
val isOnLeftEdge = {worldWidth: Int, idx: Int -> (idx % worldWidth) == 0}.curried()
val isOnFirstRow = {worldWidth: Int, idx: Int -> idx < worldWidth}.curried()

val getWithOffset = { offset: Int, outOfBound:(Int) -> Boolean, idx: Int, world: List<Char> ->
    if (outOfBound(idx)) getNone(idx) else world.getOrElse(idx+offset, ::getNone)

}.curried()

fun getNone(idx: Int) = '.'

fun isAlive(cell: Char) = cell == '*'

