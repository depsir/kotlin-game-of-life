package it.intre

typealias World = List<List<Char>>

data class Field(val row: Int, val column: Int, val isAlive: Boolean)


fun evolve(world: World): World {
    val myList = convertWorldToFieldList(world)

    val nextGeneration = myList.map { field -> evolve(field, myList) }

    return convertFieldListToWorld(nextGeneration)

}

fun evolve(field: Field, myList: Set<Field>): Field {
    //Any live cell with fewer than two live neighbors dies, as if by under population.
    //Any live cell with two or three live neighbors lives on to the next generation.
    //Any live cell with more than three live neighbors dies, as if by overpopulation.
    //Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.


    val neighbours = countAliveNeighbours(field, myList)
    when {
        field.isAlive && neighbours < 2 -> return field.copy(isAlive = false)
        field.isAlive && neighbours in listOf(2, 3) -> return field.copy(isAlive = true)
        field.isAlive && neighbours > 3 -> return field.copy(isAlive = false)
        !field.isAlive && neighbours == 3 -> return field.copy(isAlive = true)
    }

    return field
}

fun countAliveNeighbours(currentField: Field, myList: Set<Field>): Int {
    val neighbours = myList
            .filter { field -> field.column in listOf(currentField.column - 1, currentField.column, currentField.column + 1) }
            .filter { field -> field.row in listOf(currentField.row - 1, currentField.row, currentField.row + 1) }
            .filterNot { field -> field == currentField }
            .filter { field -> field.isAlive }
    return neighbours.size
}

fun convertFieldListToWorld(nextGeneration: List<Field>): World {
    val maxRow = nextGeneration.maxBy { field -> field.row }?.row ?: 0
    val maxColumn = nextGeneration.maxBy { field -> field.column }?.column ?: 0

    val rows = mutableListOf<List<Char>>()

    var rowCounter = 0
    while (rowCounter <= maxRow) {
        var columnCounter = 0
        val columns = mutableListOf<Char>()
        rows.add(columnCounter, columns)

        while (columnCounter <= maxColumn) {
            val element = nextGeneration
                    .filter { field -> field.row == rowCounter && field.column == columnCounter }
                    .map { field -> if (field.isAlive) '*' else '.' }
            assert(element.size == 1, { "element size should be 1 but is " + element.size })
            columns.add(columnCounter, element.first())
            columnCounter++
        }
        rowCounter++
    }
    return rows
}

fun convertWorldToFieldList(world: World): Set<Field> {
    val emptyList = mutableSetOf<Field>()

    for ((rowCounter, row) in world.withIndex()) {
        for ((columnCounter, column) in row.withIndex()) {
            emptyList.add(Field(rowCounter, columnCounter, column == '*'))
        }
    }
    return emptyList

}
