package it.intre

import org.junit.Assert.*
import org.junit.Test

class GameOfLifeKtTest{

    @Test
    fun `an empty world evolves in an empty world`() {
        assertEquals(World.empty<Char>(),
                     evolve(World.empty<Char>()))
    }

    @Test
    fun `a world with a single cell evolves in a world with a dead cell`() {
        assertEquals(World.of(listOf(listOf('.'))), evolve(World.of(listOf(listOf('.')))))
        assertEquals(World.of(listOf(listOf('.'))), evolve(World.of(listOf(listOf('*')))))
    }

    @Test
    fun `a cell with two neighbours stays alive`() {
        assertEquals(World.of(listOf(listOf('.','*','.'))),
                     evolve(World.of(listOf(listOf('*','*','*')))))
    }

    @Test
    fun `a 2d world!`() {
        assertEquals(World.of(listOf(listOf('.','*','.'))),
                     evolve(World.of(listOf(listOf('*','*','*')))))
    }

    @Test
    fun `a dead cell with two alive neighbours stays dead`() {
        assertEquals(World.of(listOf(listOf('.','.','.'))),
                     evolve(World.of(listOf(listOf('*','.','*')))))
    }

    @Test
    fun `neighbours can also be up or down`() {
        assertEquals(World.of(listOf(listOf('.','.','.'),
                            listOf('*','*','*'),
                            listOf('.','.','.'))),
                evolve(World.of(listOf(
                            listOf('.','*','.'),
                            listOf('.','*','.'),
                            listOf('.','*','.')))))
    }

    @Test
    fun `first row`() {
        assertEquals(World.of(listOf(listOf('.','*','.'),
                            listOf('.','*','.'),
                            listOf('.','.','.'))),
                evolve(World.of(listOf(
                            listOf('*','*','*'),
                            listOf('.','.','.'),
                            listOf('.','.','.')))))
    }
    @Test
    fun `last row`() {
        assertEquals(World.of(listOf(listOf('.','.','.'),
                            listOf('.','*','.'),
                            listOf('.','*','.'))),
                evolve(World.of(listOf(
                            listOf('.','.','.'),
                            listOf('.','.','*'),
                            listOf('*','.','*')))))
    }
    @Test
    fun `first column`() {
        assertEquals(World.of(listOf(listOf('.','.','.'),
                            listOf('*','*','.'),
                            listOf('.','.','.'))),
                evolve(World.of(listOf(
                            listOf('*','.','.'),
                            listOf('*','.','.'),
                            listOf('*','.','.')))))
    }
    @Test
    fun `last column`() {
        assertEquals(World.of(listOf(listOf('.','*','.'),
                            listOf('.','.','*'),
                            listOf('.','*','.'))),
                evolve(World.of(listOf(
                            listOf('.','.','*'),
                            listOf('*','.','*'),
                            listOf('.','.','*')))))
    }

    @Test
    fun `up left is neighbour`() {
        assertEquals(World.of(listOf(listOf('.','.','.'),
                            listOf('.','*','*'),
                            listOf('.','.','.'))),
                evolve(World.of(listOf(
                            listOf('.','*','.'),
                            listOf('.','.','*'),
                            listOf('.','.','*')))))
    }

    @Test
    fun `up right is neighbour`() {
        assertEquals(World.of(listOf(listOf('.','.','.'),
                            listOf('*','.','.'),
                            listOf('.','*','.'))),
                evolve(World.of(listOf(
                            listOf('.','*','.'),
                            listOf('*','.','.'),
                            listOf('*','.','*')))))
    }
    @Test
    fun `down right is neighbour`() {
        assertEquals(World.of(listOf(listOf('.','.','.'),
                            listOf('*','*','.'),
                            listOf('.','.','.'))),
                evolve(World.of(listOf(
                            listOf('*','.','.'),
                            listOf('*','.','.'),
                            listOf('.','*','.')))))
    }
    @Test
    fun `down left is neighbour`() {
        assertEquals(World.of(listOf(listOf('.','.','.'),
                            listOf('.','*','*'),
                            listOf('.','.','.'))),
                evolve(World.of(listOf(
                            listOf('.','*','.'),
                            listOf('.','.','*'),
                            listOf('.','*','.')))))
    }
    @Test
    fun `with three alive neighbours stays alive`() {
        assertEquals(World.of(listOf(listOf('.','*','*'),
                            listOf('.','.','*'),
                            listOf('.','.','.'))),
                evolve(World.of(listOf(
                            listOf('.','*','*'),
                            listOf('.','.','*'),
                            listOf('.','*','.')))))
    }
    @Test
    fun `with three alive neighbours becomes alive`() {
        assertEquals(World.of(listOf(listOf('.','*','*'),
                            listOf('.','*','*'),
                            listOf('.','.','.'))),
                evolve(World.of(listOf(
                            listOf('.','*','*'),
                            listOf('.','.','*'),
                            listOf('.','.','.')))))
    }

    @Test
    fun `square`() {
        assertStable(World.of(listOf(
            listOf('.', '.', '.', '.'),
            listOf('.', '*', '*', '.'),
            listOf('.', '*', '*', '.'),
            listOf('.', '.', '.', '.'))))
    }

    @Test
    fun `Beehive`() {
        assertStable(World.of(listOf(
            listOf('.', '*', '*', '.'),
            listOf('*', '.', '.', '*'),
            listOf('.', '*', '*', '.'),
            listOf('.', '.', '.', '.'))))
    }

    @Test
    fun `Loaf`() {
        assertStable(World.of(listOf(
            listOf('.', '*', '*', '.'),
            listOf('*', '.', '.', '*'),
            listOf('.', '*', '.', '*'),
            listOf('.', '.', '*', '.'))))
    }

    @Test
    fun `Boat`() {
        assertStable(World.of(listOf(
            listOf('*', '*', '.', '.'),
            listOf('*', '.', '*', '.'),
            listOf('.', '*', '.', '.'),
            listOf('.', '.', '.', '.'))))
    }

    @Test
    fun `Tub`() {
        assertStable(World.of(listOf(
            listOf('.', '.', '.', '.'),
            listOf('.', '.', '*', '.'),
            listOf('.', '*', '.', '*'),
            listOf('.', '.', '*', '.'))))
    }

    @Test
    fun `Blinker one time`() {
        assertEquals(World.of(listOf(listOf('.', '.', '.', '.'),
                            listOf('.', '.', '*', '.'),
                            listOf('.', '.', '*', '.'),
                            listOf('.', '.', '*', '.'))),
                evolve(World.of(listOf(
                            listOf('.', '.', '.', '.'),
                            listOf('.', '.', '.', '.'),
                            listOf('.', '*', '*', '*'),
                            listOf('.', '.', '.', '.')))))
    }
    @Test
    fun `Blinker two times`() {
        assertOscillator(2, World.of(listOf(
            listOf('.','.','.','.'),
            listOf('.','.','.','.'),
            listOf('.','*','*','*'),
            listOf('.','.','.','.'))))
    }
    @Test
    fun `Beacon two times`() {
        assertOscillator(2, World.of(listOf(
            listOf('*','*','.','.'),
            listOf('*','*','.','.'),
            listOf('.','.','*','*'),
            listOf('.','.','*','*'))))
    }

    fun assertOscillator(times: Int, startPattern: World<Char>){
        assertEquals(startPattern, evolveNTimes(times, startPattern))
    }

    fun assertStable(pattern: World<Char>){
        assertOscillator(1, pattern)
    }

    fun evolveNTimes(times: Int, startPattern: World<Char>): World<Char> {
        var res = startPattern
        var times1 = times
        while (times1-- >0){
            res = evolve(res)
        }
        return res
    }
}