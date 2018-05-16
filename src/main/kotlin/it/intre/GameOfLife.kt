package it.intre

fun evolve(world: List<List<Char>>): List<List<Char>> {
    return world.mapIndexed { row, list -> list.mapIndexed { col, _ -> evolveCell(world, row, col)} }
}

fun evolveCell(world: List<List<Char>>, row: Int, col: Int): Char = when (countAliveNeighbours(world, row, col)) {
    2 -> world[row][col]
    3 -> '*'
    else -> '.'
}

fun isAlive(cell: Char) = cell == '*'

fun countAliveNeighbours(world: List<List<Char>>, row: Int, col: Int): Any {
    val poss= listOf(Offset(-1,-1), Offset(-1,0), Offset(-1,1), Offset(0,-1),Offset(0,1),Offset(1,-1),Offset(1,0),Offset(1,1))
    return poss.map { x-> world.getOrNull(row+x.x)?.getOrNull(col+x.y) ?: '.'}
            .filter(::isAlive)
            .count()
}

data class Offset(val x:Int, val y:Int)
