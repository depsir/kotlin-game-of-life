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
                            listOf('*','*','*'),
                            listOf('.','.','.')),
                evolve(listOf(
                            listOf('.','*','.'),
                            listOf('.','*','.'),
                            listOf('.','*','.'))))
    }

    @Test
    fun `first row`() {
        assertEquals(listOf(listOf('.','*','.'),
                            listOf('.','*','.'),
                            listOf('.','.','.')),
                evolve(listOf(
                            listOf('*','*','*'),
                            listOf('.','.','.'),
                            listOf('.','.','.'))))
    }
    @Test
    fun `last row`() {
        assertEquals(listOf(listOf('.','.','.'),
                            listOf('.','*','.'),
                            listOf('.','*','.')),
                evolve(listOf(
                            listOf('.','.','.'),
                            listOf('.','.','*'),
                            listOf('*','.','*'))))
    }
    @Test
    fun `first column`() {
        assertEquals(listOf(listOf('.','.','.'),
                            listOf('*','*','.'),
                            listOf('.','.','.')),
                evolve(listOf(
                            listOf('*','.','.'),
                            listOf('*','.','.'),
                            listOf('*','.','.'))))
    }
    @Test
    fun `last column`() {
        assertEquals(listOf(listOf('.','*','.'),
                            listOf('.','.','*'),
                            listOf('.','*','.')),
                evolve(listOf(
                            listOf('.','.','*'),
                            listOf('*','.','*'),
                            listOf('.','.','*'))))
    }

    @Test
    fun `up left is neighbour`() {
        assertEquals(listOf(listOf('.','.','.'),
                            listOf('.','*','*'),
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
                            listOf('.','*','.')),
                evolve(listOf(
                            listOf('.','*','.'),
                            listOf('*','.','.'),
                            listOf('*','.','*'))))
    }
    @Test
    fun `down right is neighbour`() {
        assertEquals(listOf(listOf('.','.','.'),
                            listOf('*','*','.'),
                            listOf('.','.','.')),
                evolve(listOf(
                            listOf('*','.','.'),
                            listOf('*','.','.'),
                            listOf('.','*','.'))))
    }
    @Test
    fun `down left is neighbour`() {
        assertEquals(listOf(listOf('.','.','.'),
                            listOf('.','*','*'),
                            listOf('.','.','.')),
                evolve(listOf(
                            listOf('.','*','.'),
                            listOf('.','.','*'),
                            listOf('.','*','.'))))
    }
    @Test
    fun `with three alive neighbours stays alive`() {
        assertEquals(listOf(listOf('.','*','*'),
                            listOf('.','.','*'),
                            listOf('.','.','.')),
                evolve(listOf(
                            listOf('.','*','*'),
                            listOf('.','.','*'),
                            listOf('.','*','.'))))
    }
    @Test
    fun `with three alive neighbours becomes alive`() {
        assertEquals(listOf(listOf('.','*','*'),
                            listOf('.','*','*'),
                            listOf('.','.','.')),
                evolve(listOf(
                            listOf('.','*','*'),
                            listOf('.','.','*'),
                            listOf('.','.','.'))))
    }

    @Test
    fun `square`() {
        assertStable(listOf(
            listOf('.', '.', '.', '.'),
            listOf('.', '*', '*', '.'),
            listOf('.', '*', '*', '.'),
            listOf('.', '.', '.', '.')))
    }

    @Test
    fun `Beehive`() {
        assertStable(listOf(
            listOf('.', '*', '*', '.'),
            listOf('*', '.', '.', '*'),
            listOf('.', '*', '*', '.'),
            listOf('.', '.', '.', '.')))
    }

    @Test
    fun `Loaf`() {
        assertStable(listOf(
            listOf('.', '*', '*', '.'),
            listOf('*', '.', '.', '*'),
            listOf('.', '*', '.', '*'),
            listOf('.', '.', '*', '.')))
    }

    @Test
    fun `Boat`() {
        assertStable(listOf(
            listOf('*', '*', '.', '.'),
            listOf('*', '.', '*', '.'),
            listOf('.', '*', '.', '.'),
            listOf('.', '.', '.', '.')))
    }

    @Test
    fun `Tub`() {
        assertStable(listOf(
            listOf('.', '.', '.', '.'),
            listOf('.', '.', '*', '.'),
            listOf('.', '*', '.', '*'),
            listOf('.', '.', '*', '.')))
    }

    @Test
    fun `Blinker one time`() {
        assertEquals(listOf(listOf('.', '.', '.', '.'),
                            listOf('.', '.', '*', '.'),
                            listOf('.', '.', '*', '.'),
                            listOf('.', '.', '*', '.')),
                evolve(listOf(
                            listOf('.', '.', '.', '.'),
                            listOf('.', '.', '.', '.'),
                            listOf('.', '*', '*', '*'),
                            listOf('.', '.', '.', '.'))))
    }
    @Test
    fun `Blinker two times`() {
        assertOscillator(2, listOf(
            listOf('.','.','.','.'),
            listOf('.','.','.','.'),
            listOf('.','*','*','*'),
            listOf('.','.','.','.')))
    }
    @Test
    fun `Beacon two times`() {
        assertOscillator(2, listOf(
            listOf('*','*','.','.'),
            listOf('*','*','.','.'),
            listOf('.','.','*','*'),
            listOf('.','.','*','*')))
    }

    fun assertOscillator(times: Int, startPattern: World){
        assertEquals(startPattern, evolveNTimes(times, startPattern))
    }

    fun assertStable(pattern: World){
        assertOscillator(1, pattern)
    }

    fun evolveNTimes(times: Int, startPattern: World): World {
        var res = startPattern
        var times1 = times
        while (times1-- >0){
            res = evolve(res)

        }
        return res
    }


}


