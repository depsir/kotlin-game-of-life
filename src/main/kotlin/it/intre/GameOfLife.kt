package it.intre

import arrow.syntax.function.*

val map = { transform: (kotlin.Int, Char, List<Char>) -> Char, collection: kotlin.collections.List<Char> ->
    collection.mapIndexed { idx: Int, e: Char -> transform.invoke(idx, e, collection) }
}.curried()
val filter = {predicate: (Char) -> Boolean, collection: kotlin.collections.List<Char> ->
    collection.filter(predicate)
}.curried()
val count = {collection:List<Char> -> collection.size }
val flatten = { world: List<List<Char>> -> world.flatten()}
val windowed = { size: Int, step: Int, flatWorld:List<Char> ->
    flatWorld.windowed(size, step)
}.curried()
infix fun <IP1, IP2, IP3,  R, P1> ((IP1, IP2, IP3) -> P1).andThen(f: (P1) -> R): (IP1, IP2, IP3) -> R {
    return { ip1:  IP1, ip2:IP2, ip3:IP3 -> f(this(ip1,ip2,ip3)) }
}
val and = {a:(Int)->Boolean, b:(Int)->Boolean, v:Int -> a(v) && b(v)}.curried()

fun evolve(world: List<List<Char>>): List<List<Char>> {
    val worldWidth = world.first().size
    val evolveWorld = flatten andThen map(evolveCell.partially1(worldWidth)) andThen windowed(worldWidth)(worldWidth)
    return if (worldWidth == 0) world else evolveWorld(world)
}

val evolveCell = {worldWidth:Int, idx: Int, cell: Char, world: List<Char> ->
    if (countAliveNeighbours(worldWidth, idx, world) == 2) cell else '.'}

val getNeighbours = {worldWidth:Int, idx: Int, world: List<Char> -> neighborhood.map { it.invoke(worldWidth)(idx)(world)}}

val neighborhood = listOf(::before, ::after, ::up, ::down, ::upLeft, ::upRight, ::downRight, ::downLeft)
fun before(worldWidth:Int) = getWithOffset(- 1)(isOnLeftEdge(worldWidth))
fun after(worldWidth:Int) = getWithOffset(+ 1)(isOnRightEdge(worldWidth))
fun up(worldWidth:Int) = getWithOffset(- worldWidth)(isOnFirstRow(worldWidth))
fun down(worldWidth:Int) = getWithOffset(+ worldWidth)({_:Int -> false})
fun upLeft(worldWidth:Int) = getWithOffset(-1- worldWidth)(and(isOnFirstRow(worldWidth))(isOnLeftEdge(worldWidth)))
fun upRight(worldWidth:Int) = getWithOffset(1- worldWidth)(and(isOnFirstRow(worldWidth))(isOnRightEdge(worldWidth)))
fun downRight(worldWidth:Int) = getWithOffset(1+ worldWidth)(isOnRightEdge(worldWidth))
fun downLeft(worldWidth:Int) = getWithOffset(-1+ worldWidth)(isOnLeftEdge(worldWidth))

val isOnRightEdge = {worldWidth: Int, idx: Int -> (idx + 1) % worldWidth == 0}.curried()
val isOnLeftEdge = {worldWidth: Int, idx: Int -> (idx % worldWidth) == 0}.curried()
val isOnFirstRow: (Int) -> (Int) -> Boolean = { worldWidth: Int, idx: Int -> idx < worldWidth}.curried()

val getWithOffset = { offset: Int, outOfBound:(Int) -> Boolean, idx: Int, world: List<Char> ->
    if (outOfBound(idx)) getNone(idx) else world.getOrElse(idx+offset, ::getNone)

}.curried()

val countAliveNeighbours = getNeighbours andThen filter(::isAlive) andThen count

fun getNone(idx: Int) = '.'

fun isAlive(cell: Char) = cell == '*'

