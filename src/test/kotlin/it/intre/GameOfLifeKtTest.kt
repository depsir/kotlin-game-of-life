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

    @Test
    fun `a dead cell with two alive neighbours stays dead`() {
        assertEquals(listOf(listOf('.','.','.')), evolve(listOf(listOf('*','.','*'))))
    }

    @Test
    fun `neighbours can also be up or down`() {
        assertEquals(listOf(listOf('.','.','.'),
                            listOf('.','*','.'),
                            listOf('.','.','.')),
                evolve(listOf(
                            listOf('.','*','.'),
                            listOf('.','*','.'),
                            listOf('.','*','.'))))
    }

    @Test
    fun `first row`() {
        assertEquals(listOf(listOf('.','*','.'),
                            listOf('.','.','.'),
                            listOf('.','.','.')),
                evolve(listOf(
                            listOf('*','*','*'),
                            listOf('.','.','.'),
                            listOf('.','.','.'))))
    }
    @Test
    fun `last row`() {
        assertEquals(listOf(listOf('.','.','.'),
                            listOf('.','.','.'),
                            listOf('.','*','.')),
                evolve(listOf(
                            listOf('.','.','.'),
                            listOf('.','.','.'),
                            listOf('*','*','*'))))
    }
    @Test
    fun `first column`() {
        assertEquals(listOf(listOf('.','.','.'),
                            listOf('*','.','.'),
                            listOf('.','.','.')),
                evolve(listOf(
                            listOf('*','.','.'),
                            listOf('*','.','.'),
                            listOf('*','.','.'))))
    }
    @Test
    fun `last column`() {
        assertEquals(listOf(listOf('.','.','.'),
                            listOf('.','.','*'),
                            listOf('.','.','.')),
                evolve(listOf(
                            listOf('.','.','*'),
                            listOf('*','.','*'),
                            listOf('.','.','*'))))
    }

    @Test
    fun `up left is neighbour`() {
        assertEquals(listOf(listOf('.','.','.'),
                            listOf('.','.','*'),
                            listOf('.','.','.')),
                evolve(listOf(
                            listOf('.','*','.'),
                            listOf('.','.','*'),
                            listOf('.','.','*'))))
    }

    @Test
    fun `up right is neighbour`() {
        assertEquals(listOf(listOf('.','.','.'),
                            listOf('*','.','.'),
                            listOf('.','.','.')),
                evolve(listOf(
                            listOf('.','*','.'),
                            listOf('*','.','.'),
                            listOf('*','.','*'))))
    }
    @Test
    fun `down right is neighbour`() {
        assertEquals(listOf(listOf('.','.','.'),
                            listOf('*','.','.'),
                            listOf('.','.','.')),
                evolve(listOf(
                            listOf('*','.','.'),
                            listOf('*','.','.'),
                            listOf('.','*','.'))))
    }
    @Test
    fun `down left is neighbour`() {
        assertEquals(listOf(listOf('.','.','.'),
                            listOf('.','.','*'),
                            listOf('.','.','.')),
                evolve(listOf(
                            listOf('.','*','.'),
                            listOf('.','.','*'),
                            listOf('.','*','.'))))
    }
}


