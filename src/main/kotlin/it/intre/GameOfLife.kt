package it.intre

fun evolve(world: List<Char>): List<Char> {
    return world.myMap(::evolveCell)
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

fun <T, R> kotlin.collections.List<T>.myMap(transform: (kotlin.Int, T, List<T>) -> R): kotlin.collections.List<R> {
    return this.mapIndexed { idx: Int, e: T -> transform.invoke(idx, e, this) }
}

