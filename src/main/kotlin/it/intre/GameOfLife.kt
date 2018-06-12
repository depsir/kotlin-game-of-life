package it.intre

typealias World = List<List<Char>>

data class Field(val row: Int, val column: Int, val isAlive: Boolean)


fun evolve(world: World): World {
    val currentGeneration = convertWorldToFieldList(world)
    val nextGeneration = currentGeneration.map { field ->
        val noOfNeighbours = countAliveNeighbours(field, currentGeneration)
        evolveField(field, noOfNeighbours)
    }
    return convertFieldListToWorld(nextGeneration)
}

fun evolveField(field: Field, noOfNeighbours: Int): Field {
    return when {
        field.isAlive && noOfNeighbours < 2 -> dead(field)
        field.isAlive && noOfNeighbours in listOf(2, 3) -> alive(field)
        field.isAlive && noOfNeighbours > 3 -> dead(field)
        !field.isAlive && noOfNeighbours == 3 -> alive(field)
        else -> field
    }
}

private fun countAliveNeighbours(currentField: Field, fields: Set<Field>): Int {
    val neighbours = fields
            .filter { field -> field.column in listOf(currentField.column - 1, currentField.column, currentField.column + 1) }
            .filter { field -> field.row in listOf(currentField.row - 1, currentField.row, currentField.row + 1) }
            .filterNot { field -> field == currentField }
            .filter { field -> field.isAlive }
    return neighbours.size
}

private fun alive(field: Field) = field.copy(isAlive = true)

private fun dead(field: Field) = field.copy(isAlive = false)

private fun convertFieldListToWorld(nextGeneration: List<Field>): World {

    if (nextGeneration.isEmpty()) {
        return listOf(emptyList())
    }

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
    return rows.reversed()
}

private fun convertWorldToFieldList(world: World): Set<Field> {
    val emptyList = mutableSetOf<Field>()

    for ((rowCounter, row) in world.withIndex()) {
        for ((columnCounter, column) in row.withIndex()) {
            emptyList.add(Field(rowCounter, columnCounter, column == '*'))
        }
    }
    return emptyList

}
