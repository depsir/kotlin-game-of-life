package it.intre

import org.junit.Assert.*
import org.junit.Test

class GameOfLifeKtTest{
    @Test
    fun `an empty world evolves in an empty world`() {
        assertEquals(listOf<List<Char>>(listOf()), evolve(listOf(listOf())))
    }

    @Test
    fun `a world with a single cell evolves in a world with a dead cell`() {
        assertEquals(listOf(listOf('.')), evolve(listOf(listOf('.'))))
        assertEquals(listOf(listOf('.')), evolve(listOf(listOf('*'))))
    }

    @Test
    fun `a cell with two neighbours stays alive`() {
        assertEquals(listOf(listOf('.','*','.')), evolve(listOf(listOf('*','*','*'))))
    }

    @Test
    fun `a 2d world!`() {
        assertEquals(listOf(listOf('.','*','.')), evolve(listOf(listOf('*','*','*'))))
    }

}


